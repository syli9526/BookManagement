package ServerUI;

import DataStructure.BookData;
import Server.ServerAppManger;
import Server.ServerConstant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ServerRentPanel extends JPanel {
	/*
	 * 대여 정보를 관리하는 패널 모든 메소드들은 Priamary패널과 bookPanel에서 설명한 메소드의 흐름과 똑같이 따르고 있다.
	 */

    private JComboBox<String> combox;
    private JLabel labUserID;
    private JLabel labBookID;
    private JTextField txtSearchInfo;
    private JTextField txtUserID;
    private JTextField txtBookID;
    private JButton btnSearch;
    private JButton btnRental;
    private JButton btnReturn;
    private JButton btnEnter;
    private JTable tableBook;
    private DefaultTableModel model;
    private JScrollPane scroll;

    public ServerRentPanel() {

        ServerAppManger.getS_instance().setServerRentPanel(this);
        setLayout(null);
        SetUI();

    }

    private void CreateComponent() {
        combox = new JComboBox(ServerConstant.RENT_INFO);
        labUserID = new JLabel(ServerConstant.USER_INFO[0]);
        labBookID = new JLabel(ServerConstant.BOOK_INFO[0]);

        txtSearchInfo = new JTextField();
        txtUserID = new JTextField();
        txtBookID = new JTextField();

        btnSearch = new JButton(ServerConstant.SERACH);
        btnRental = new JButton(ServerConstant.RENTAL);
        btnReturn = new JButton(ServerConstant.RETURN);
        btnEnter = new JButton(ServerConstant.ENTER);
        model = new DefaultTableModel();
        model.setColumnIdentifiers(ServerConstant.RENT_INFO);
        tableBook = new JTable(model);
        scroll = new JScrollPane(tableBook);

    }

    private void AddComponent() {

        add(combox);

        add(labUserID);
        add(labBookID);

        add(txtSearchInfo);
        add(txtUserID);
        add(txtBookID);

        add(btnSearch);
        add(btnRental);
        add(btnReturn);
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

        labBookID.setBounds(480, 180, 100, 70);
        txtBookID.setBounds(600, 180, 150, 70);

        btnRental.setBounds(490, 350, 105, 70);
        btnReturn.setBounds(630, 350, 105, 70);
        btnEnter.setBounds(630, 20, 80, 60);

        scroll.setBounds(20, 90, 440, 370);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        AddComponent();

    }

    public void refreshData(ArrayList<BookData> datas,boolean bool) {

        clearField();

        if(bool) {
            if (tableBook.getSelectedRow() > -1) {
                int selectRow = tableBook.getSelectedRow();
                String data[] = new String[tableBook.getColumnCount()];

                for (int i = 0; i < tableBook.getColumnCount(); i++) {
                    data[i] = (String) tableBook.getValueAt(selectRow, i);

                }

                txtUserID.setText(data[4]);
                txtBookID.setText(data[0]);

            }
        }

        tableBook.clearSelection();

        model.setRowCount(0);
        Object[] rowData = new Object[ServerConstant.RENT_INFO.length];

        if (datas != null) {
            for (BookData b : datas) {

                rowData[0] = b.getBookID();
                rowData[1] = b.getBookName();
                rowData[2] = b.getAuthor();
                rowData[3] = b.getPublish();
                rowData[4] = b.getRentUserID();
                rowData[5] = b.getStartDate();
                rowData[6] = b.getEndDate();
                model.addRow(rowData);
            }
        }


    }

    public void clearField() {

        txtBookID.setText("");
        txtUserID.setText("");
        txtSearchInfo.setText("");
    }

    public void AddEvent(ActionListener act) {
        combox.addActionListener(act);
        btnSearch.addActionListener(act);
        btnRental.addActionListener(act);
        btnReturn.addActionListener(act);
        btnEnter.addActionListener(act);
    }

    public BookData getRentInfo() {
        return new BookData(txtBookID.getText(),txtUserID.getText());
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

    public JTextField getTxtUserID() {
        return txtUserID;
    }

    public void setTxtUserID(JTextField txtUserID) {
        this.txtUserID = txtUserID;
    }

    public JTextField getTxtBookID() {
        return txtBookID;
    }

    public void setTxtBookID(JTextField txtBookID) {
        this.txtBookID = txtBookID;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JButton getBtnRental() {
        return btnRental;
    }

    public void setBtnRental(JButton btnRental) {
        this.btnRental = btnRental;
    }

    public JButton getBtnReturn() {
        return btnReturn;
    }

    public void setBtnReturn(JButton btnReturn) {
        this.btnReturn = btnReturn;
    }

    public JTable getTableBook() {
        return tableBook;
    }

    public void setTableBook(JTable tableBook) {
        this.tableBook = tableBook;
    }

    public JButton getBtnEnter() {
        return btnEnter;
    }

    public void setBtnEnter(JButton btnEnter) {
        this.btnEnter = btnEnter;
    }
}
