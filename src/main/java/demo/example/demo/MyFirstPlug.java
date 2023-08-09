package demo.example.demo;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import demo.example.demo.form.InterfaceForm;
import org.jetbrains.annotations.NotNull;



public class MyFirstPlug extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
//        System.out.println("is my first plug");
//        Project project = e.getProject();
//        Messages.showMessageDialog(project, "This is message content", "This Message Title", Messages.getInformationIcon());
        Project project = e.getProject();
        InterfaceForm dialog = new InterfaceForm(project);
        dialog.pack();
        dialog.setVisible(true);
//        终止程序
//        System.exit(0);

    }
}
