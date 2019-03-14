package ServerUI;

import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DataStructure.UserData;
import Server.ServerAppManger;
import Server.ServerConstant;

public class ServerUserPanel extends JPanel {
	/*
	 * 사용자 정보를 관리하는 패널 모든 메소드들은 처음에 설명한 primaryPanel과 bookPanel에서 설명한것과 같은 논리를 따르고 있다.
	 */
    private JComboBox<String> combox;
    private JLabel labUserID;
    private JLabel labUserPassword;
    private JLabel labUserName;
    private JTextField txtSearchInfo;
    private JTextField txtUserID;
    private JTextField txtUserPassword;
    private JTextField txtUserName;
    private JButton btnSearch;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnEnter;
    private JTable tableBook;
    private DefaultTableModel model;
    private JScrollPane scroll;

    public ServerUserPanel() {
        ServerAppManger.getS_instance().setsUserPanel(this);
        setLayout(null);
        SetUI();

    }

    private void CreateComponent() {
        combox = new JComboBox(ServerConstant.USER_COMBO);
        labUserID = new JLabel(ServerConstant.USER_INFO[0]);
        labUserPassword = new JLabel(ServerConstant.USER_INFO[1]);
        labUserName = new JLabel(ServerConstant.USER_INFO[2]);
        txtSearchInfo = new JTextField();
        txtUserID = new JTextField();
        txtUserPassword = new JTextField();
        txtUserName = new JTextField();
        btnSearch = new JButton(ServerConstant.SERACH);
        btnInsert = new JButton(ServerConstant.INSERT);
        btnUpdate = new JButton(ServerConstant.UPDATE);
        btnDelete = new JButton(ServerConstant.DELETE);
        btnEnter = new JButton(ServerConstant.ENTER);
        model = new DefaultTableModel();
        model.setColumnIdentifiers(ServerConstant.USER_INFO);
        tableBook = new JTable(model);
        scroll = new JScrollPane(tableBook);

    }

    private void AddComponent() {
        add(combox);
        add(labUserID);
        add(labUserPassword);
        add(labUserName);
        add(txtSearchInfo);
        add(txtUserID);
        add(txtUserPassword);
        add(txtUserName);
        add(btnSearch);
        add(btnInsert);
        add(btnUpdate);
        add(btnDelete);
        add(btnEnter);
        add(scroll);
    }

    private void SetUI() {
        CreateComponent();
        combox.setBounds(20, 20, 150, 50);
        txtSearchInfo.setBounds(180, 20, 180, 50);
        btnSearch.setBounds(380, 20, 80, 50);// 480~760

        labUserID.setBounds(480, 90, 100, 70);
        txtUserID.setBounds(600, 90, 150, 70);

        labUserPassword.setBounds(480, 180, 100, 70);
        txtUserPassword.setBounds(600, 180, 150, 70);

        labUserName.setBounds(480, 270, 100, 70);
        txtUserName.setBounds(600, 270, 150, 70);

        btnInsert.setBounds(480, 350, 70, 70);
        btnUpdate.setBounds(570, 350, 70, 70);
        btnDelete.setBounds(660, 350, 70, 70);
        btnEnter.setBounds(630, 20, 80, 60);

        scroll.setBounds(20, 90, 440, 370);

        AddComponent();
    }


    public void refreshData(ArrayList<UserData> datas, boolean bool) {

        clearField();

        if (bool) {
            if (tableBook.getSelectedRow() > -1) {
                int selectRow = tableBook.getSelectedRow();
                String data[] = new String[tableBook.getColumnCount() - 1];

                for (int i = 0; i < tableBook.getColumnCount() - 1; i++) {
                    data[i] = (String) tableBook.getValueAt(selectRow, i);

                }
                txtUserID.setText(data[0]);
                txtUserPassword.setText(data[1]);
                txtUserName.setText(data[2]);

            }
        }

        tableBook.clearSelection();

        model.setRowCount(0);
        Object rowData[] = new Object[ServerConstant.USER_INFO.length];

        if (datas != null) {

            for (UserData u : datas) {

                rowData[0] = u.getUserID();
                rowData[1] = u.getUserPassword();
                rowData[2] = u.getUserName();
                rowData[3] = u.isFlag();

                model.addRow(rowData);

            }
        }
    }

    public void clearField() {

        txtUserName.setText("");
        txtUserID.setText("");
        txtSearchInfo.setText("");
        txtUserPassword.setText("");
    }

    public void AddEvent(ActionListener act) {
        combox.addActionListener(act);
        btnSearch.addActionListener(act);
        btnInsert.addActionListener(act);
        btnUpdate.addActionListener(act);
        btnDelete.addActionListener(act);
        btnEnter.addActionListener(act);
    }

    public UserData getUserInfo() {
        return new UserData(txtUserID.getText(), txtUserPassword.getText(), txtUserName.getText());
    }

    public void setUserData(UserData d) {

    }

    public void setUserID(String s) {
        txtUserID.setText(s);
    }

    public void setPassWord(String s) {
        txtUserPassword.setText(s);
    }

    public void setUserName(String s) {
        txtUserName.setText(s);
    }

    public String getUserID() {
        return txtUserID.getText();
    }

    public String getPassWord() {
        return txtUserPassword.getText();
    }

    public String getUserName() {
        return txtUserName.getText();
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }

    public void setBtnInsert(JButton btnInsert) {
        this.btnInsert = btnInsert;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JComboBox<String> getCombox() {
        return combox;
    }

    public void setCombox(JComboBox<String> combox) {
        this.combox = combox;
    }

    public JTextField getTxtSearchInfo() {
        return txtSearchInfo;
    }

    public void setTxtSearchInfo(JTextField txtSearchInfo) {
        this.txtSearchInfo = txtSearchInfo;
    }

    public JButton getBtnEnter() {
        return btnEnter;
    }

    public void setBtnEnter(JButton btnEnter) {
        this.btnEnter = btnEnter;
    }
}
