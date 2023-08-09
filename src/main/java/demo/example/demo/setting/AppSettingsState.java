package demo.example.demo.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@State(
        name = "SettingsState",
        storages = @Storage("SettingsPlugin.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public String author = "byzhao";

    public boolean autoDate = true;

    public String excelPath = "D:\\贵州股交接口文档\\";

    public String extensions = "xls,xlsx";

    public String interfaceClass = "com.gemantic.wealth.consignment.service.ConsignmentService";

    public String serviceClass = "com.gemantic.wealth.consignment.service.impl.ConsignmentServiceImpl";

    public String entityIn = "com.gemantic.wealth.consignment.model.gjs";

    public String entityOut = "com.gemantic.wealth.consignment.model.gjs";


    public String entityInCommon = "com.gemantic.wealth.consignment.model.in.CommonIn";

    public String entityOutCommon = "com.gemantic.wealth.consignment.model.out.CommonOut";
    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    //定义自己为持久化数据类
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
