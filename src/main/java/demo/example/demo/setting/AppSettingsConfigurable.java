package demo.example.demo.setting;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nullable;
import javax.swing.*;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "SDK: Application Settings Example";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modifiedName = !mySettingsComponent.getUserNameText().equals(settings.author);
        boolean modifiedExcel = !mySettingsComponent.getExcelShortcutDirectory().equals(settings.excelPath);
        boolean modifiedStatus = mySettingsComponent.getIdeaUserStatus() != settings.autoDate;
        boolean modifiedExtensions = !mySettingsComponent.getExtensionsText().equals(settings.extensions);
        boolean modifiedInterFace = !mySettingsComponent.getInterfacePackageText().equals(settings.interfaceClass);
        boolean modifiedService = !mySettingsComponent.getServicePackageText().equals(settings.serviceClass);
        boolean modifiedEntityIn = !mySettingsComponent.getEntityInText().equals(settings.entityIn);
        boolean modifiedEntityOut = !mySettingsComponent.getEntityOutText().equals(settings.entityOut);
        boolean modifiedEntityOutCommon = !mySettingsComponent.getEntityInCommonText().equals(settings.entityOutCommon);
        boolean modifiedEntityInCommon = !mySettingsComponent.getEntityInCommonText().equals(settings.entityInCommon);
        return modifiedName || modifiedExcel || modifiedStatus || modifiedExtensions || modifiedInterFace || modifiedService || modifiedEntityIn || modifiedEntityOut || modifiedEntityOutCommon || modifiedEntityInCommon;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.author = mySettingsComponent.getUserNameText();
        settings.autoDate = mySettingsComponent.getIdeaUserStatus();
        settings.excelPath = mySettingsComponent.getExcelShortcutDirectory();
        settings.extensions = mySettingsComponent.getExtensionsText();
        settings.interfaceClass = mySettingsComponent.getInterfacePackageText();
        settings.serviceClass = mySettingsComponent.getServicePackageText();
        settings.entityOut = mySettingsComponent.getEntityOutText();
        settings.entityIn = mySettingsComponent.getEntityInText();
        settings.entityInCommon = mySettingsComponent.getEntityInCommonText();
        settings.entityOutCommon = mySettingsComponent.getEntityOutCommonText();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setUserNameText(settings.author);
        mySettingsComponent.setIdeaUserStatus(settings.autoDate);
        mySettingsComponent.setExcelShortcutDirectory(settings.excelPath);
        mySettingsComponent.setExtensionsText(settings.extensions);
        mySettingsComponent.setServicePackageText(settings.serviceClass);
        mySettingsComponent.setInterfacePackageText(settings.interfaceClass);
        mySettingsComponent.setEntityInText(settings.entityIn);
        mySettingsComponent.setEntityOutText(settings.entityOut);
        mySettingsComponent.setEntityOutCommonText(settings.entityOutCommon);
        mySettingsComponent.setEntityInCommonText(settings.entityInCommon);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
