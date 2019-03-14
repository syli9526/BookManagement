package ServerUI;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DataStructure.BookData;
import Server.ServerAppManger;
import Server.ServerConstant;

public class ServerBookPanel extends JPanel {
	/*
	 * 책을 관리하는 부분의 패널이다.
	 */

    private JComboBox<String> combox;
    private JLabel labBookName;
    private JLabel labBookAuthor;
    private JLabel labBookPublish;
    private JLabel labBookID;
    private JTextField txtSearchInfo;
    private JTextField txtBookName;
    private JTextField txtBookAuthor;
    private JTextField txtBookPublish;
    private JTextField txtBookID;
    private JButton btnEnter;
    private JButton btnSearch;
    private JButton btnInsert;
    private JButton btnDelete;
    private JTable tableBook;
    private DefaultTableModel model;
    private JScrollPane scroll;


    public ServerBookPanel() {
    	//생성자
        ServerAppManger.getS_instance().setsBookPanel(this);
        // setBackground(Color.BLACK);
        setLayout(null);
        SetUI();
    }

    private void CreateComponent() {
    	//여러 컴포넌트들의 초기 생성
        combox = new JComboBox(ServerConstant.BOOK_INFO);
        labBookID = new JLabel(ServerConstant.BOOK_INFO[0]);
        labBookName = new JLabel(ServerConstant.BOOK_INFO[1]);
        labBookAuthor = new JLabel(ServerConstant.BOOK_INFO[2]);
        labBookPublish = new JLabel(ServerConstant.BOOK_INFO[3]);
        txtBookID = new JTextField();
        txtSearchInfo = new JTextField();
        txtBookName = new JTextField();
        txtBookAuthor = new JTextField();
        txtBookPublish = new JTextField();
        btnEnter = new JButton(ServerConstant.ENTER);
        btnSearch = new JButton(ServerConstant.SERACH);
        btnInsert = new JButton(ServerConstant.INSERT);
        btnDelete = new JButton(ServerConstant.DELETE);
        model = new DefaultTableModel();
        model.setColumnIdentifiers(ServerConstant.BOOK_INFO);
        tableBook = new JTable(model);
        tableBook.setPreferredScrollableViewportSize(tableBook.getPreferredSize());
        tableBook.setFillsViewportHeight(true);
        scroll = new JScrollPane(tableBook);
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }

    private void AddComponent() {
    	//여러 컴포넌트들을 애드
        add(combox);
        add(labBookID);
        add(labBookName);
        add(labBookAuthor);
        add(labBookPublish);
        add(txtBookID);
        add(txtSearchInfo);
        add(txtBookName);
        add(txtBookAuthor);
        add(txtBookPublish);
        add(btnEnter);
        add(btnSearch);
        add(btnInsert);
        add(btnDelete);
        add(scroll);

    }

    private void SetUI() {
    	//UI의 배치를 초기에 설정하는 메소드
        CreateComponent();
        combox.setBounds(20, 20, 150, 50);
        txtSearchInfo.setBounds(180, 20, 180, 50);
        btnSearch.setBounds(380, 20, 80, 50);// 480~760

        labBookID.setBounds(480, 90, 100, 60);
        txtBookID.setBounds(600, 90, 150, 60);

        labBookName.setBounds(480, 170, 100, 60);
        txtBookName.setBounds(600, 170, 150, 60);

        labBookAuthor.setBounds(480, 240, 100, 60);
        txtBookAuthor.setBounds(600, 240, 150, 60);

        labBookPublish.setBounds(480, 320, 100, 60);
        txtBookPublish.setBounds(600, 320, 150, 60);

        btnInsert.setBounds(490, 410, 105, 60);
        btnDelete.setBounds(630, 410, 105, 60);
        btnEnter.setBounds(630,20,80, 60);

        scroll.setBounds(20, 90, 440, 370);

        AddComponent();
    }

    public void refreshData(ArrayList<BookData> datas, boolean bool) {
    	//데이터를 받아서 테이블에 반영하고자 만들어둔 메소드

        clearField();
        Object[]rowData = new Object[ServerConstant.BOOK_INFO.length];

        if(bool) {
            if (tableBook.getSelectedRow() > -1) {
                int selectRow = tableBook.getSelectedRow();
                String data[] = new String[tableBook.getColumnCount()];

                for (int i = 0; i < tableBook.getColumnCount(); i++) {
                    data[i] = (String) tableBook.getValueAt(selectRow, i);

                }

                txtBookID.setText(data[0]);
                txtBookName.setText(data[1]);
                txtBookAuthor.setText(data[2]);
                txtBookPublish.setText(data[3]);
                //--> 테이블에서 선택한 정보에 대해 텍스트 필드에 반영하고자 만들어둔 부분
            }
        }
       tableBook.clearSelection();

        model.setRowCount(0);

        if(datas!=null){

            for (BookData b : datas){

                rowData[0] = b.getBookID();
                rowData[1] = b.getBookName();
                rowData[2] = b.getAuthor();
                rowData[3] = b.getPublish();
                rowData[4] = b.getRentUserID();
                rowData[5] = b.getReservationUserID();
                rowData[6] = b.getStartDate();
                rowData[7] = b.getEndDate();

                model.addRow(rowData);
            }

        }

    }

    public void clearField() {
    	//--> 텍스트 필드를 빈칸으로 만들어 주는 메소드

        txtBookAuthor.setText("");
        txtBookName.setText("");
        txtBookID.setText("");
        txtBookPublish.setText("");
        txtSearchInfo.setText("");
    }

    public void AddEvent(ActionListener act) {
    	//이벤트 리스너를 애드하는 메소드
        btnSearch.addActionListener(act);
        btnInsert.addActionListener(act);
        btnDelete.addActionListener(act);
        btnEnter.addActionListener(act);
    }

    /*
     * 여러 컴포넌트들의 (get / set )ter
     */
    public BookData getBookInfo() {
        return new BookData(txtBookID.getText(), txtBookName.getText(), txtBookAuthor.getText(), txtBookPublish.getText());
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

    public JTextField getTxtBookName() {
        return txtBookName;
    }

    public void setTxtBookName(JTextField txtBookName) {
        this.txtBookName = txtBookName;
    }

    public JTextField getTxtBookAuthor() {
        return txtBookAuthor;
    }

    public void setTxtBookAuthor(JTextField txtBookAuthor) {
        this.txtBookAuthor = txtBookAuthor;
    }

    public JTextField getTxtBookPublish() {
        return txtBookPublish;
    }

    public void setTxtBookPublish(JTextField txtBookPublish) {
        this.txtBookPublish = txtBookPublish;
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

    public JButton getBtnInsert() {
        return btnInsert;
    }

    public void setBtnInsert(JButton btnInsert) {
        this.btnInsert = btnInsert;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
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
