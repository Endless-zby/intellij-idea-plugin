package demo.example.demo.setting;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class AppSettingsComponent {
    private final JPanel myMainPanel;
    private final JBTextField myUserNameText = new JBTextField();
    private final JBTextField excelShortcutDirectory = new JBTextField();

    private final JBTextField extensionsText = new JBTextField();

    private final JBTextField entityOutText = new JBTextField();

    private final JBTextField entityInText = new JBTextField();
    private final JBTextField interfaceClassText = new JBTextField();
    private final JBTextField serviceClassText = new JBTextField();

    private final JBCheckBox autoAddTimeStamp = new JBCheckBox("Auto add time stamp ? ");

    private final JBTextField entityOutCommonText = new JBTextField();

    private final JBTextField entityInCommonText = new JBTextField();

    public AppSettingsComponent() {

        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter user name: "), myUserNameText, 1, false)
                .addComponent(setJSeparator())

                .addLabeledComponent(new JBLabel("Enter excel directory: "), excelShortcutDirectory, 1, false)
                .addLabeledComponent(new JBLabel("Enter excel extensions: "), extensionsText, 1, false)

                .addComponent(setJSeparator())


                .addLabeledComponent(new JBLabel("Core entity in: "), entityInText, 1, false)
                .addLabeledComponent(new JBLabel("Core entity out: "), entityOutText, 1, false)
                .addLabeledComponent(new JBLabel("Interface class: "), interfaceClassText, 1, false)
                .addLabeledComponent(new JBLabel("Service class: "), serviceClassText, 1, false)

                .addComponent(setJSeparator())

                .addLabeledComponent(new JBLabel("Core entity in common: "), entityInCommonText, 1, false)
                .addLabeledComponent(new JBLabel("core entity out common: "), entityOutCommonText, 1, false)
                .addComponent(setJSeparator())
                .addComponent(autoAddTimeStamp, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JSeparator setJSeparator(){
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        // 设置分割线的样式和大小
        separator.setForeground(Color.GRAY);
        return separator;
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myUserNameText;
    }

    @NotNull
    public String getUserNameText() {
        return myUserNameText.getText();
    }

    public void setUserNameText(@NotNull String newText) {
        myUserNameText.setText(newText);
    }

    @NotNull
    public String getExcelShortcutDirectory() {
        return excelShortcutDirectory.getText();
    }

    public void setExcelShortcutDirectory(@NotNull String newText) {
        excelShortcutDirectory.setText(newText);
    }

    @NotNull
    public String getExtensionsText() {
        return extensionsText.getText();
    }

    public void setExtensionsText(@NotNull String newText) {
        extensionsText.setText(newText);
    }

    public boolean getIdeaUserStatus() {
        return autoAddTimeStamp.isSelected();
    }

    public void setIdeaUserStatus(boolean newStatus) {
        autoAddTimeStamp.setSelected(newStatus);
    }

    @NotNull
    public String getInterfacePackageText() {
        return interfaceClassText.getText();
    }

    public void setInterfacePackageText(@NotNull String newText) {
        interfaceClassText.setText(newText);
    }

    public String getServicePackageText() {
        return serviceClassText.getText();
    }

    public void setServicePackageText(@NotNull String newText) {
        serviceClassText.setText(newText);
    }

    public String getEntityOutText() {
        return entityOutText.getText();
    }

    public void setEntityOutText(@NotNull String newText) {
        entityOutText.setText(newText);
    }

    public String getEntityInText() {
        return entityInText.getText();
    }

    public void setEntityInText(@NotNull String newText) {
        entityInText.setText(newText);
    }

    public String getEntityInCommonText() {
        return entityInCommonText.getText();
    }

    public void setEntityInCommonText(@NotNull String newText) {
        entityInCommonText.setText(newText);
    }

    public String getEntityOutCommonText() {
        return entityOutCommonText.getText();
    }

    public void setEntityOutCommonText(@NotNull String newText) {
        entityOutCommonText.setText(newText);
    }
}
