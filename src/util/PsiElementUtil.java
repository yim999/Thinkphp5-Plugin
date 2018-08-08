package util;

import beans.ParameterBag;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.ParameterListOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiElementUtil {

    public static boolean isFunctionReference(@NotNull PsiElement psiElement, @NotNull String functionName, int parameterIndex) {

        PsiElement parameterList = psiElement.getParent();
        if (!(parameterList instanceof ParameterList)) {
            return false;
        }
        ParameterBag index = getCurrentParameterIndex(psiElement);
        if (index == null || index.getIndex() != parameterIndex) {
            return false;
        }
        PsiElement functionCall = parameterList.getParent();
        if (!(functionCall instanceof FunctionReference)) {
            return false;
        }
        return functionName.equals(((FunctionReference) functionCall).getName());
    }

    @Nullable
    public static ParameterBag getCurrentParameterIndex(PsiElement psiElement) {

        if (!(psiElement.getContext() instanceof ParameterList)) {
            return null;
        }

        ParameterList parameterList = (ParameterList) psiElement.getContext();
        if (!(parameterList.getContext() instanceof ParameterListOwner)) {
            return null;
        }

        return getCurrentParameterIndex(parameterList.getParameters(), psiElement);
    }

    @Nullable
    public static ParameterBag getCurrentParameterIndex(PsiElement[] parameters, PsiElement parameter) {
        int i;
        for (i = 0; i < parameters.length; i = i + 1) {
            if (parameters[i].equals(parameter)) {
                return new ParameterBag(i, parameters[i]);
            }
        }

        return null;
    }
}
