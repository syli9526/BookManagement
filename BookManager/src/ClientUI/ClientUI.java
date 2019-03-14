package ClientUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Client.ClientAppManager;
import Client.ClientConstant;
import DataStructure.BookData;

public class ClientUI extends JPanel {

	/*
	 * �� ��Ʈ�� Ŭ���̾�Ʈ�� �г� ��Ʈ
	 */
	private JPanel upperPanel, leftPanel, rightPanel;
	/*
	 * Ŭ���̾�Ʈ ���α׷��� ��� 3���� ū �гη� �����Ǿ� �ִ� ù��°�� upperPanel�� �α��� �α׾ƿ� �������� ���̴� �г��̴�.
	 * �ι����� ���ʿ� ���������� ���� ������ ���̰� �Ǵ� �гη� �� �г��� �̸��� leftPanel�� ��Ұ� �����ʿ��� �˻��� ���Ҿ�
	 * �˻��� ����� ������ �ϴ� ���̺��� ���� �����Ƿ� �̸� rightPanel�� �̸��� ������.
	 */
	
	private JPanel loginPanel, logoutPanel;
	private JTextField txtID;
	private JPasswordField txtPW;
	private JButton btnLogin, btnRegist;
	private String id;
	private JLabel lblID;
	private JButton btnLogout, btnExit;
	private CardLayout cardLayout;
	//upperPanel�� �����ϴ� ������Ʈ��

	private JScrollPane scrollBookInfo, scrollRevList;
	private JTable reservedList;
	private DefaultTableModel modelReserveInfo;
	private JButton btnReserve;
	private JTable bookRentInfo;
	private DefaultTableModel modelRentInfo;
	//leftPanel�� �����ϴ� ������Ʈ��
	
	private JComboBox cbCategory;
	private JTextField txtSearch;
	private JButton btnSearch;
	private JScrollPane scrollBookList;
	private JTable bookList;
	private DefaultTableModel modelBookInfo;
	//rightPanel�� �����ϴ� ������Ʈ��

	private Window parentWindow;
	private static JFrame parentFrame;
	private JPanel pane;
	private String regID, regPW, regName;
	char[] regsavePW;
	//�� â�� ȸ�������� ���� ���̾�α׸� �����ϰ� �̶� �ްԵ� ȸ������������ ���� ������� ��� ������

	private Object value;

	public ClientUI() {
		ClientAppManager.getClinetAppManager().setClientUI(this);
		// ������
	}

	/*
	 * Ŭ���̾�Ʈ UI�� �����ϴ� �޼ҵ�
	 */
	public void createUI() {
		setLayout(new BorderLayout(20, 20));
		//3���� �г��� ���� ���̾ƿ��� ���� �����ߴ�. �� �гε� ���̴� �����¿� 20pixel�� �������� �������.
		/*
		 * upperPanel�� �� �����κ�
		 * cardLayout�� ����Ͽ� ����
		 */
		upperPanel = new JPanel();
		upperPanel.setPreferredSize(new Dimension(1000, 80));
		cardLayout = new CardLayout();
		upperPanel.setLayout(cardLayout);

		loginPanel = new JPanel();
		loginPanel.setPreferredSize(new Dimension(1000, 80));
		loginPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 30));
		txtID = new JTextField(10);
		txtPW = new JPasswordField(10);
		btnLogin = new JButton("Login");
		btnRegist = new JButton("Regist");

		loginPanel.add(txtID);
		loginPanel.add(txtPW);
		loginPanel.add(btnLogin);
		loginPanel.add(btnRegist);

		logoutPanel = new JPanel();
		logoutPanel.setPreferredSize(new Dimension(1000, 80));
		logoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 30));
		id = "???";
		lblID = new JLabel(id + "�� ȯ���մϴ�.");
		btnLogout = new JButton("Logout");
		btnExit = new JButton("Exit");

		logoutPanel.add(lblID);
		logoutPanel.add(btnLogout);
		logoutPanel.add(btnExit);

		upperPanel.add(loginPanel, "login");
		upperPanel.add(logoutPanel, "logout");
		cardLayout.show(upperPanel, "login");
		this.add(upperPanel, BorderLayout.NORTH);
		/*
		 * LeftPanel�� �󼼱���
		 * �� �κ��� ���̾ƿ� �Ŵ����� ������� �ʰ� ���� setBounds�� �Ͽ� �����Ͽ���.
		 */
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(500, 580));
		leftPanel.setLayout(null);
		modelRentInfo = new DefaultTableModel();
		modelRentInfo.setColumnIdentifiers(ClientConstant.bookInfoheader);
		bookRentInfo = new JTable(modelRentInfo);
		scrollBookInfo = new JScrollPane(bookRentInfo);
		scrollBookInfo.setBounds(10, 10, 480, 250);
		modelReserveInfo = new DefaultTableModel();
		modelReserveInfo.setColumnIdentifiers(ClientConstant.bookReserveInfoheader);
		reservedList = new JTable(modelReserveInfo);
		scrollRevList = new JScrollPane(reservedList);
		scrollRevList.setBounds(10, 270, 480, 250);
		// (10,270,480,250)
		btnReserve = new JButton("Reserve");
		btnReserve.setBounds(200, 530, 100, 30);

		leftPanel.add(scrollBookInfo);
		leftPanel.add(scrollRevList);
		leftPanel.add(btnReserve);

		this.add(leftPanel, BorderLayout.WEST);
		/*
		 * rightPanel�� �� �����κ� ���� �гΰ� ���� ���̾ƿ� �ų����� ������� �ʾ���.
		 */
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(500, 580));
		rightPanel.setLayout(null);

		cbCategory = new JComboBox(ClientConstant.comboBoxStirng);
		cbCategory.setBounds(70, 10, 100, 30);
		txtSearch = new JTextField(10);
		txtSearch.setBounds(170, 10, 120, 30);
		btnSearch = new JButton("Search");
		btnSearch.setBounds(330, 10, 100, 30);
		modelBookInfo = new DefaultTableModel();
		modelBookInfo.setColumnIdentifiers(ClientConstant.bookReserveInfoheader);
		bookList = new JTable(modelBookInfo);
		scrollBookList = new JScrollPane(bookList);
		scrollBookList.setBounds(10, 50, 480, 470);

		rightPanel.add(cbCategory);
		rightPanel.add(txtSearch);
		rightPanel.add(btnSearch);
		rightPanel.add(scrollBookList);

		this.add(rightPanel, BorderLayout.EAST);

	}

	public void createRegistPane() {
		/*
		 * ȸ�� ������ ���� ���̾�α׸� �����Ͽ� ����� �ִ� �κ� 
		 * �� �г��� ������ ���� ����� �˻��� ���� Ȯ���Ͽ� �����Ͽ���.
		 */
		parentWindow = SwingUtilities.windowForComponent(this);
		//�켱 ���̾�α׸� ����� ���ؼ��� �� �г��� ������ �������� ���� �˾ƾ� �ϹǷ� �̸� �˰� ���ִ� �޼ҵ带 ���
		if (parentWindow instanceof JFrame) {
			parentFrame = (JFrame) parentWindow;
		}
		//JFrame�� �´ٸ� JFrame���� parentWindow�� ĳ���� �ؼ� parentFrame�� �����Ѵ�.
		pane = new JPanel();
		pane.setLayout(new GridLayout(0, 2, 2, 2));
		//�г��� ����� �׸��� ���̾ƿ��� ����Ͽ���.

		JTextField idField = new JTextField(5);
		JPasswordField pwField = new JPasswordField(5);
		JTextField nameField = new JTextField(5);
		//���̵� ��й�ȣ ����� �̸��� ���� �ؽ�Ʈ �ʵ带 ���� ��й�ȣ�� ��� JPaasswordField�� ���

		pane.add(new JLabel("ID"));
		pane.add(idField);
		pane.add(new JLabel("PW"));
		pane.add(pwField);
		pane.add(new JLabel("Name"));
		pane.add(nameField);

		int option = JOptionPane.showConfirmDialog(parentFrame, pane, "����� ȸ�� ������ �����ּ���.", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		//�� ���̾�α��� ��� OK - CANCEL 2���� �������� �����ϴ� ���̾�α׷� ����� �ξ���.

		if (option == JOptionPane.OK_OPTION) {
			regID = idField.getText();
			regsavePW = pwField.getPassword();
			regName = nameField.getText();
			try {
				regPW = String.valueOf(regsavePW);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//���� OK ��� ȸ������ ������ �����ڴٴ� ������ Ǯ�̵Ǿ� ������ ������ �� �������� ���� ��Ʈ������
			//�����Ͽ� ������ �ִ´�.
			//�̋� JPasswordField�� ��� char[] ���·� ���� ������ ���� ��Ʈ������ �����ؾ� �ϹǷ� ������ �ѹ� ��ģ��.
		}

	}

	public void addActionListener(ActionListener l) {
		btnLogin.addActionListener(l);
		btnRegist.addActionListener(l);
		btnLogout.addActionListener(l);
		btnExit.addActionListener(l);
		btnSearch.addActionListener(l);
		btnReserve.addActionListener(l);
	}
	//�� �гο� ������� ��ư���� �̺�Ʈ �����ʸ� ���̴� �޼ҵ�

	public JButton getbtnLogin() {
		return btnLogin;
	}
	//�α��� ��ư�� ��ȯ�޴� �޼ҵ�

	public JButton getbtnRegist() {
		return btnRegist;
	}
	//ȸ������ ��ư�� ��ȯ�޴� �޼ҵ�

	public JButton getbtnLogout() {
		return btnLogout;
	}
	//�α׾ƿ� ��ư�� ��ȯ�޴� �޼ҵ�

	public JButton getbtnExit() {
		return btnExit;
	}
	//�����ư�� ��ȯ�޴� �޼ҵ�

	public JButton getbtnSearch() {
		return btnSearch;
	}
	//�˻� ��ư�� ��ȯ�޴� �޼ҵ�

	public JButton getbtnReserve() {
		return btnReserve;
	}
	//���� ��ư�� ��ȯ�޴� �޼ҵ�

	public String loginID() {
		return txtID.getText();
	}
	//�α��� ID�� ��ȯ�޴� �޼ҵ� ==> ��, ���� �� Ŭ���̾�Ʈ ���α׷��� �α����ϰ��� Ȥ�� �α��ε� ID�� ��ȯ�ϴ� �޼ҵ�

	public String loginPW() {
		char[] savePW = txtPW.getPassword();
		return String.valueOf(savePW);
	}
	//�н����� ��, �α����� �õ��ϴ� ���̵�� �Բ� ��й�ȣ�� ��ȯ�޴� �޼ҵ�

	public String getRegID() {
		return regID;
	}

	public String getRegPW() {
		return regPW;
	}

	public String getRegName() {
		return regName;
	}

	/*
	 * ȸ�� ���� ������ �޴� �޼ҵ�
	 */
	
	public void setUserId(String id) {
		this.id = id;
		lblID.setText(id + " �� ȯ���մϴ�.");
	}
	//�гο� �α��ε� ���ξ��̵� �����ϴ� �޼ҵ�

	public void showLoginPanel() {
		cardLayout.show(upperPanel, "login");
		txtID.setText("");
		txtPW.setText("");
	}
	//�α��� �г��� �����ִ� �޼ҵ�

	public Object getBookTableValue() {
		int row = bookList.getSelectedRow();
		int col = bookList.getSelectedColumn();
		value = bookList.getValueAt(row, col);
		return value;
	}
	// å ���� �� �˻��� ���̺��� ������ ������ �޴� �޼ҵ�

	public Object getReserveTableValue() {
		int row = reservedList.getSelectedRow();
		int col = reservedList.getSelectedColumn();
		value = reservedList.getValueAt(row, col);
		return value;
	}
	//�������� �� �������� ���̺��� ������ ������ �޴� �޼ҵ�

	public BookData getSearchData() {
		BookData bookData = new BookData();
		Object category = cbCategory.getSelectedItem();
		if (category.toString() == "book name") {
			bookData.setBookName(txtSearch.getText());
		} else if (category.toString() == "author") {
			bookData.setAuthor(txtSearch.getText());
		} else if (category.toString() == "publish") {
			bookData.setPublish(txtSearch.getText());
		} else if(category.toString() == "All") {
		
		}
		/*
		 * �˻��� �ϰ� �� å ������ ��� � Ű���带 ������ �˻��ߴ����� �޺��ڽ��� �������� �ٸ��Ƿ�
		 * �̸� �ݿ��ϱ� ���� ������ ��츦 �б�� ������ ������ �� �̋� all�̸� ��� å ������ �� �޾ƿ��ڴٴ� ���̹Ƿ�
		 * Ư���� å ������ ������ �������� �ʴ´�.
		 */
		return bookData;
	}

	public BookData getReservedData() {
		BookData bookData = new BookData();
		int row = bookList.getSelectedRow();
		for(int i = 0;i < 4; i++) {
			value = bookList.getValueAt(row, i);
			if (i == 1) {
				bookData.setBookName(value.toString());
			} else if (i == 2) {
				bookData.setAuthor(value.toString());
			} else if (i == 3) {
				bookData.setPublish(value.toString());
			} else if(i == 0) {
				bookData.setBookID(value.toString());
			}
		}
		/*
		 * å�� �����ϱ� ���ؼ��� å�� ������ �̸� �����ؾ� �ϹǷ� ������ ������ bookData�� �ϳ� ���� �������� ��
		 * �� ������ ��ȯ�Ѵ�.
		 */
		return bookData;
	}

	public void refreshBookInfo(ArrayList<BookData> datas) {
		/*
		 * �˻��� å ������ ������ ���� �޾ƿͼ� �̸� UI�� �ѷ��ִ� �޼ҵ�
		 */
		bookList.clearSelection();
		//���̺��� ������ ��� �����Ѵ�.
		modelBookInfo.setRowCount(0);
		Object[] rowData = new Object[ClientConstant.bookReserveInfoheader.length];
		//�켱 å ���̺��� ������ ���� ���� ���缭 ������Ʈ�� ���̸� �����ϰ� ���� ���� 0���� �����Ѵ�.
		if (datas != null) {
			//���� ���� data �Ķ���Ͱ� ���� �ƴ϶��
			for (BookData b : datas) {
				rowData[0] = b.getBookID();
				rowData[1] = b.getBookName();
				rowData[2] = b.getAuthor();
				rowData[3] = b.getPublish();
				modelBookInfo.addRow(rowData);
			}
			//for - each���� ���� ��� �����͸� ���� å �˻� ���̺� ������ �ѷ��ִ� model�� �޾Ƽ� �ֵ��Ѵ�.
		}
		bookList.repaint();
		scrollBookList.repaint();
		//���� ���̺��� ��� repaint�Ͽ� �ٲ� ������ �����Ѵ�.
	}

	public void refreshBookReserveInfo(ArrayList<BookData> datas) {
		//���� ������ ������ ���� �޾Ƽ� UI�� �ѷ��ִ� �޼ҵ�
		//�� ���� ���� ���� refresh �޼ҵ忡�� ���� �����߰� �̰� ���� ���̺� ����Ǿ��� ����
		reservedList.clearSelection();

		modelReserveInfo.setRowCount(0);
		Object[] rowData = new Object[ClientConstant.bookReserveInfoheader.length];

		if (datas != null) {
			for (BookData b : datas) {
				rowData[0] = b.getBookID();
				rowData[1] = b.getBookName();
				rowData[2] = b.getAuthor();
				rowData[3] = b.getPublish();
				modelReserveInfo.addRow(rowData);
			}
		}
		else {
			
		}
		reservedList.repaint();
		scrollRevList.repaint();
	}

	public void refreshBookRentInfo(ArrayList<BookData> datas) {
		//���� ������ ������ ���� �޾Ƽ� UI�� �ѷ��ִ� �޼ҵ�
		//�� ���� ���� ���� refresh �޼ҵ忡�� ���� �����߰� �̰� ���� ���̺� ����Ǿ��� ����
		bookRentInfo.clearSelection();

		modelRentInfo.setRowCount(0);
		Object[] rowData = new Object[ClientConstant.bookInfoheader.length];

		if (datas != null) {
			for (BookData b : datas) {
				rowData[0] = b.getBookID();
				rowData[1] = b.getBookName();
				rowData[2] = b.getAuthor();
				rowData[3] = b.getPublish();
				rowData[4] = b.getStartDate();
				rowData[5] = b.getEndDate();
				modelRentInfo.addRow(rowData);
			}
		}
		else {
			
		}
		bookRentInfo.repaint();
		scrollBookInfo.repaint();
	}
	
	public void showLogoutPanel() {
		cardLayout.show(upperPanel, "logout");
	}
	//�α׾ƿ� �г��� ���� �÷��ִ� �޼ҵ�

}
