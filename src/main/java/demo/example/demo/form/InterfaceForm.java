package demo.example.demo.form;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.google.common.collect.Lists;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFileManager;
import demo.example.demo.InterfaceDetails;
import demo.example.demo.entity.InterfaceDocEntity;
import demo.example.demo.entity.OutDoc;
import demo.example.demo.factory.GenerateCodeEntityFactoryPlus;
import demo.example.demo.listener.InterfaceDocEntityListener;
import demo.example.demo.setting.AppSettingsState;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.TreeMap;

public class InterfaceForm extends JDialog{

    private JPanel contentPane;
    private JButton cancelButton;
    private JButton importButton;
    private JTextField textField1;
    private JTable sheetTable;
    private JButton commit;
    private JList interfaceList;
    private JButton settingButton;
    private Project project;

    // 持久化配置
    private AppSettingsState state = AppSettingsState.getInstance().getState();


    private static final String SEPARATOR = "-";
    // 创建表格数据数组
    private static final Object[][] defaultData = {{0,"defaultData", "Action"}};
    private static final Object[] columnNames = {"SheetNo", "SheetName", "Action"};
    private static String filePath = null;
    private static TreeMap<String, OutDoc> outDocTree = new TreeMap<>();

    // 选中的function
    private static List<String> functions = Lists.newArrayList();

    public InterfaceForm(Project project) {
        setTitle("指定接口");
        this.project = project;
        commit.setEnabled(false);
        setContentPane(contentPane);
        setModal(false);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(importButton);
        initSheetTable(defaultData, columnNames);
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 代码生成器
        commit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                Messages.showMessageDialog(String.valueOf(functions.size()), "FunctionId", Messages.getInformationIcon());
                GenerateCodeEntityFactoryPlus generateCodeEntityFactory = new GenerateCodeEntityFactoryPlus(outDocTree, functions);
                try {
                    generateCodeEntityFactory.generateCode(project);
                    // 实体类是直接写文件到项目目录中的，手动从磁盘刷新下目录比较快
                    VirtualFileManager.getInstance().syncRefresh();
                }catch (Exception exception){
                    Messages.showMessageDialog(exception.getMessage(), "Error", Messages.getErrorIcon());

                }
            }
        });

        settingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowSettingsUtil.getInstance().showSettingsDialog(project, "Generate Code");
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        sheetTable.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = sheetTable.getSelectedRow();
                    Integer sheetId = (Integer)sheetTable.getValueAt(row, 0);
                    System.out.println(sheetId);
                    ExcelReader reader;
                    try {
                        outDocTree.clear();
                        reader = EasyExcel.read(new FileInputStream(filePath), InterfaceDocEntity.class, new InterfaceDocEntityListener(outDocTree)).build();
                    } catch (Exception exception) {
                        System.out.println("excel解析异常");
                        Messages.showMessageDialog(String.format("excel解析异常[ %s ]：", filePath, exception.getMessage()), "异常", Messages.getWarningIcon());
                        throw new RuntimeException(exception);
                    }
                    ReadSheet readSheet = EasyExcel.readSheet(sheetId).build();
                    reader.read(readSheet);
                    initInterfaceList(outDocTree);
                }
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here

        analysisExcelSheet();

//        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void analysisExcelSheet() {
        // add your code here if necessary
        JFileChooser fileChooser;
        if(StringUtils.isNotEmpty(state.excelPath)){
            fileChooser = new JFileChooser(state.excelPath);
        }else {
            fileChooser = new JFileChooser();
        }
        fileChooser.setMultiSelectionEnabled(false);
        String[] extensions = state.extensions.split(",");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "杭州接口文档 (*.xls, *.xlsx)", extensions);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = fileChooser.showOpenDialog(InterfaceForm.this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String excelPath = file.getAbsolutePath();
            filePath = excelPath;
            setTitle("excelPath");
            textField1.setText(excelPath);
            System.out.println("准备开始解析excel数据");
            List<ReadSheet> readSheets;
            try {
                ExcelReaderBuilder read = EasyExcel.read(new FileInputStream(excelPath));
                readSheets = read.build().excelExecutor().sheetList();
            } catch (FileNotFoundException e) {
                System.out.println("excel解析异常");
                Messages.showMessageDialog(String.format("excel解析异常[ %s ]", excelPath), "异常", Messages.getWarningIcon());
                throw new RuntimeException(e);
            }
            Object[][] dataArray = new Object[readSheets.size()][2];
            // 遍历list1
            for (int i = 0; i < readSheets.size(); i++) {
                ReadSheet obj = readSheets.get(i);

                // 将对象的属性存入二维数组中
                dataArray[i][0] = obj.getSheetNo();
                dataArray[i][1] = obj.getSheetName();
            }
            initSheetTable(dataArray, columnNames);
        } else {
            textField1.setText("");
        }
    }

    private void initSheetTable(Object[][] data, Object[] columnNames) {
        // 创建默认表格模型
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // 最后一列是按钮列，设置为不可编辑
                return false;
            }
        };
        sheetTable.setModel(tableModel);
        TableColumn buttonColumn = sheetTable.getColumnModel().getColumn(columnNames.length - 1);
        buttonColumn.setCellRenderer(new InterfaceForm.ButtonRenderer());
    }
    private void initInterfaceList(TreeMap<String, OutDoc> outDocTreeMap) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<OutDoc> list = outDocTreeMap.values().stream().toList();
        for (OutDoc outDoc: list) {
            listModel.addElement(String.format("%s%s%s", outDoc.getFunId(),SEPARATOR, outDoc.getFunName()));
        }
        interfaceList.setModel(listModel);
        ListSelectionModel selectionModel = interfaceList.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int[] selectedIndices = interfaceList.getSelectedIndices();
                commit.setEnabled(true);
                functions.clear();
                for (int id: selectedIndices) {
                    if (id != -1) {
                        // 处理选择的逻辑操作
                        System.out.println("Selected item: " + listModel.getElementAt(id));
                        functions.add(listModel.getElementAt(id).split(SEPARATOR)[0]);
                    }
                }
            }
        });

        interfaceList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = interfaceList.locationToIndex(evt.getPoint());
                    String selectedData = listModel.getElementAt(index);
//                    Messages.showMessageDialog(selectedData, "FunctionId", Messages.getInformationIcon());
                    InterfaceDetails.showInterfaceDetails( outDocTree.get(selectedData.split(SEPARATOR)[0]));
                }
            }
        });
    }
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("解析");
            return this;
        }
    }

}
