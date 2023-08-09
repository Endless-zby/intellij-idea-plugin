package demo.example.demo.form;

import com.intellij.openapi.ui.Messages;
import demo.example.demo.entity.InterfaceDocEntity;
import demo.example.demo.entity.OutDoc;
import demo.example.demo.util.InterfaceDocEntityUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class InterfaceDetailsForm extends JDialog{
    private JPanel panel1;
    private JTable table1;
    private JButton cancelButton;
    private static final Object[] columnNames = InterfaceDocEntityUtil.getExcelPropertyValues();

    private OutDoc outDoc;

    public InterfaceDetailsForm(OutDoc outDoc){
        if(outDoc == null){
            Messages.showErrorDialog("请重新解析Excel","Error");
        }
        setTitle(outDoc.getFunId() + "-" + outDoc.getFunName());
        setContentPane(panel1);
        setModal(false);
        setLocationRelativeTo(null);
//        setSize(400, 300);
        setPreferredSize(new Dimension(1000, 300));
        this.outDoc = outDoc;
        List<InterfaceDocEntity> interfaceDocEntities = outDoc.getInterfaceDocEntities();
        Object[][] dataArray = new Object[interfaceDocEntities.size()][columnNames.length];

        // 遍历list1
        for (int i = 0; i < interfaceDocEntities.size(); i++) {
            InterfaceDocEntity interfaceDocEntity = interfaceDocEntities.get(i);
            Map<String, Object> excelPropertyValues = InterfaceDocEntityUtil.getPropertyMap(interfaceDocEntity);

            // 将对象的属性存入二维数组中
            for (int j = 0; j < columnNames.length; j++) {
                dataArray[i][j] = excelPropertyValues.get(columnNames[j]);
            }
        }
        initInterfaceTable(dataArray, columnNames);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        panel1.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void initInterfaceTable(Object[][] data, Object[] columnNames) {
        // 创建默认表格模型
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        table1.setModel(tableModel);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel columnModel = table1.getColumnModel();  // 获取列模型
        TableColumn column = columnModel.getColumn(0);
        column.setPreferredWidth(60);
        column = columnModel.getColumn(1);
        column.setPreferredWidth(150);
        column = columnModel.getColumn(2);
        column.setPreferredWidth(60);
        column = columnModel.getColumn(3);
        column.setPreferredWidth(150);
        column = columnModel.getColumn(5);
        column.setPreferredWidth(300);
    }
}
