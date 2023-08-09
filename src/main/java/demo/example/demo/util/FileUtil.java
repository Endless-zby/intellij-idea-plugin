package demo.example.demo.util;

import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;


public class FileUtil {

    public final static String EMPTY_STRING = "";


    public static void generateCodeToFile(String className, Map<String, Object> data, String templateMode, String outputPath ) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        String path = outputPath + className + ".java";
        try {
            cfg.setClassForTemplateLoading(FileUtil.class, "/");
            Template template = cfg.getTemplate(templateMode);
            try (Writer writer = new FileWriter(path)) {
                data.put("randomUUID", String.valueOf(java.util.UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE));
                template.process(data, writer);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            Messages.showMessageDialog(e.getMessage(), "Error-" + path, Messages.getErrorIcon());
        }
    }

    public static String generateCodeToString(Map<String, Object> data, String templateMode) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        try {
            cfg.setClassForTemplateLoading(FileUtil.class, "/");
            Template template = cfg.getTemplate(templateMode);
            // 将模板和数据合并 输出
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            Messages.showMessageDialog(e.getMessage(), "Error", Messages.getErrorIcon());
        }
        return null;
    }
    public static void generateAndInsertMethod(Project project, PsiClass targetClass, String methodBody) {
        try {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
                String replace = methodBody.replace("\r\n","\n");
                // 创建新的方法
                PsiMethod newMethod = elementFactory.createMethodFromText(replace, targetClass);
                PsiElement addedMethod = targetClass.add(newMethod);

                // 导入缺失的类
                PsiFile containingFile = addedMethod.getContainingFile();
                if (containingFile instanceof PsiJavaFile) {
                    JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(project);
                    styleManager.optimizeImports(containingFile);
                }
                // 格式化代码
                CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);
                codeStyleManager.reformat(addedMethod);

                // 定位光标至插入的方法处
                NavigationUtil.activateFileWithPsiElement(addedMethod);
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor != null) {
                    editor.getCaretModel().moveToOffset(addedMethod.getTextOffset());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void generateAndInsertMethods(Project project, PsiClass targetClass, List<String> methodCodes) {
        try {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
                JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(project);
                CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);
                // 循环遍历每个方法体
                for (String methodCode: methodCodes){

                    String replace = methodCode.replace("\r\n", "\n");
                    // 创建新的方法
                    PsiMethod newMethod = elementFactory.createMethodFromText(replace, targetClass);
                    PsiElement addedMethod = targetClass.add(newMethod);
                    // 导入缺失的类
                    PsiFile containingFile = addedMethod.getContainingFile();
                    if (containingFile instanceof PsiJavaFile) {
                        styleManager.optimizeImports(containingFile);
                    }
                    // 格式化代码
                    codeStyleManager.reformat(addedMethod);
                    // 更新进度条
                }
                // 保存修改后的文件
                PsiDocumentManager.getInstance(project).commitAllDocuments();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
