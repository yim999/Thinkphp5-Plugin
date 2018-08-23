package util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vcs.history.VcsRevisionNumber;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.indexing.FileBasedIndex;
import com.jetbrains.php.lang.PhpFileType;
import router.RouteValStubIndex;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tool {
    public static void log(Object obj) {
        System.out.println(obj);
    }

    public static void printPsiTree(PsiElement element) {
        if (element == null) {
            return;
        }
        level++;
        int i = 0;
        PsiElement[] elements = element.getChildren();
        for (PsiElement item : elements) {
            i++;
            printeIndent("   ", level);
            String s = item.getClass().getSimpleName();
            //level+"."+i+":"+
            System.out.println(level + " " + s);
            printPsiTree(item);
        }
        level--;
    }
    private static int level = 0;

    private static void printeIndent(String str, int cnt) {
        for (int i = 0; i < cnt; i++) {
            System.out.print(str);
        }
    }

    public static void printFileIndex(Project project) {
        final String test = "index/Index2/test";
        List<String> strings = Collections.singletonList(test);
        System.out.println(strings);

        FileBasedIndex.getInstance().getFilesWithKey(RouteValStubIndex.KEY, new HashSet<>(strings), new Processor<VirtualFile>() {
            @Override
            public boolean process(VirtualFile virtualFile) {
                String name = virtualFile.getName();
                System.out.println(name);
                return true;
            }
        }, GlobalSearchScope.getScopeRestrictedByFileTypes(GlobalSearchScope.allScope(project), PhpFileType.INSTANCE));
    }

}