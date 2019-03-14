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
	 * 뷰 파트중 클라이언트의 패널 파트
	 */
	private JPanel upperPanel, leftPanel, rightPanel;
	/*
	 * 클라이언트 프로그램의 경우 3개의 큰 패널로 구성되어 있다 첫번째가 upperPanel로 로그인 로그아웃 정보들이 보이는 패널이다.
	 * 두번쨰가 왼쪽에 대출정보와 예약 정보가 보이게 되는 패널로 이 패널의 이름을 leftPanel로 잡았고 오른쪽에는 검색과 더불어
	 * 검색한 결과가 나오게 하는 테이블이 놓여 있으므로 이를 rightPanel로 이름을 지었다.
	 */
	
	private JPanel loginPanel, logoutPanel;
	private JTextField txtID;
	private JPasswordField txtPW;
	private JButton btnLogin, btnRegist;
	private String id;
	private JLabel lblID;
	private JButton btnLogout, btnExit;
	private CardLayout cardLayout;
	//upperPanel을 구성하는 컴포넌트들

	private JScrollPane scrollBookInfo, scrollRevList;
	private JTable reservedList;
	private DefaultTableModel modelReserveInfo;
	private JButton btnReserve;
	private JTable bookRentInfo;
	private DefaultTableModel modelRentInfo;
	//leftPanel을 구성하는 컴포넌트들
	
	private JComboBox cbCategory;
	private JTextField txtSearch;
	private JButton btnSearch;
	private JScrollPane scrollBookList;
	private JTable bookList;
	private DefaultTableModel modelBookInfo;
	//rightPanel을 구성하는 컴포넌트들

	private Window parentWindow;
	private static JFrame parentFrame;
	private JPanel pane;
	private String regID, regPW, regName;
	char[] regsavePW;
	//이 창에 회원가입을 위한 다이얼로그를 구성하고 이때 받게될 회원가입정보와 각종 내용들을 담는 변수들

	private Object value;

	public ClientUI() {
		ClientAppManager.getClinetAppManager().setClientUI(this);
		// 생성자
	}

	/*
	 * 클라이언트 UI를 생성하는 메소드
	 */
	public void createUI() {
		setLayout(new BorderLayout(20, 20));
		//3개의 패널은 보더 레이아웃을 통해 구현했다. 각 패널들 사이는 상하좌우 20pixel씩 떨어지게 만들었다.
		/*
		 * upperPanel의 상세 구현부분
		 * cardLayout을 사용하여 구현
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
		lblID = new JLabel(id + "님 환영합니다.");
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
		 * LeftPanel의 상세구현
		 * 이 부분은 레이아웃 매니저를 사용하지 않고 직접 setBounds를 하여 구현하였다.
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
		 * rightPanel의 상세 구현부분 왼쪽 패널과 같이 레이아웃 매너저를 사용하지 않았음.
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
		 * 회원 가입을 위한 다이얼로그를 정의하여 만들어 주는 부분 
		 * 이 패널의 생성을 위한 방법은 검색을 통해 확인하여 제작하였다.
		 */
		parentWindow = SwingUtilities.windowForComponent(this);
		//우선 다이얼로그를 만들기 위해서는 이 패널의 주인인 프레임이 뭔지 알아야 하므로 이를 알게 해주는 메소드를 사용
		if (parentWindow instanceof JFrame) {
			parentFrame = (JFrame) parentWindow;
		}
		//JFrame이 맞다면 JFrame으로 parentWindow를 캐스팅 해서 parentFrame에 저장한다.
		pane = new JPanel();
		pane.setLayout(new GridLayout(0, 2, 2, 2));
		//패널을 만들고 그리드 레이아웃을 사용하였다.

		JTextField idField = new JTextField(5);
		JPasswordField pwField = new JPasswordField(5);
		JTextField nameField = new JTextField(5);
		//아이디 비밀번호 사용자 이름을 적는 텍스트 필드를 선언 비밀번호의 경우 JPaasswordField를 사용

		pane.add(new JLabel("ID"));
		pane.add(idField);
		pane.add(new JLabel("PW"));
		pane.add(pwField);
		pane.add(new JLabel("Name"));
		pane.add(nameField);

		int option = JOptionPane.showConfirmDialog(parentFrame, pane, "등록할 회원 정보를 적어주세요.", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		//이 다이얼로그의 경우 OK - CANCEL 2개의 선택지가 존재하는 다이얼로그로 만들어 두었다.

		if (option == JOptionPane.OK_OPTION) {
			regID = idField.getText();
			regsavePW = pwField.getPassword();
			regName = nameField.getText();
			try {
				regPW = String.valueOf(regsavePW);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//만약 OK 라면 회원가입 정보를 보내겠다는 것으로 풀이되어 서버에 보내게 될 정보들을 각각 스트링으로
			//저장하여 가지고 있는다.
			//이떄 JPasswordField의 경우 char[] 형태로 먼저 저장한 다음 스트링으로 저장해야 하므로 과정을 한번 거친다.
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
	//이 패널에 만들어진 버튼들의 이벤트 리스너를 붙이는 메소드

	public JButton getbtnLogin() {
		return btnLogin;
	}
	//로그인 버튼을 반환받는 메소드

	public JButton getbtnRegist() {
		return btnRegist;
	}
	//회원가입 버튼을 반환받는 메소드

	public JButton getbtnLogout() {
		return btnLogout;
	}
	//로그아웃 버튼을 반환받는 메소드

	public JButton getbtnExit() {
		return btnExit;
	}
	//종료버튼을 반환받는 메소드

	public JButton getbtnSearch() {
		return btnSearch;
	}
	//검색 버튼을 반환받는 메소드

	public JButton getbtnReserve() {
		return btnReserve;
	}
	//예약 버튼을 반환받는 메소드

	public String loginID() {
		return txtID.getText();
	}
	//로그인 ID를 반환받는 메소드 ==> 즉, 현재 이 클라이언트 프로그램에 로그인하고자 혹은 로그인된 ID를 반환하는 메소드

	public String loginPW() {
		char[] savePW = txtPW.getPassword();
		return String.valueOf(savePW);
	}
	//패스워드 즉, 로그인을 시도하는 아이디와 함께 비밀번호를 반환받는 메소드

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
	 * 회원 가입 정보를 받는 메소드
	 */
	
	public void setUserId(String id) {
		this.id = id;
		lblID.setText(id + " 님 환영합니다.");
	}
	//패널에 로그인된 주인아이디를 설정하는 메소드

	public void showLoginPanel() {
		cardLayout.show(upperPanel, "login");
		txtID.setText("");
		txtPW.setText("");
	}
	//로그인 패널을 보여주는 메소드

	public Object getBookTableValue() {
		int row = bookList.getSelectedRow();
		int col = bookList.getSelectedColumn();
		value = bookList.getValueAt(row, col);
		return value;
	}
	// 책 정보 즉 검색한 테이블에서 선택한 정보를 받는 메소드

	public Object getReserveTableValue() {
		int row = reservedList.getSelectedRow();
		int col = reservedList.getSelectedColumn();
		value = reservedList.getValueAt(row, col);
		return value;
	}
	//예약정보 즉 예약정보 테이블에서 선택한 정보를 받는 메소드

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
		 * 검색을 하고난 후 책 정보의 경우 어떤 키워드를 가지고 검색했는지가 콤보박스의 값에따라 다르므로
		 * 이를 반영하기 위해 각각의 경우를 분기로 나누어 설정함 단 이떄 all이면 모든 책 정보를 다 받아오겠다는 것이므로
		 * 특별히 책 정보에 내용을 설정하지 않는다.
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
		 * 책을 예약하기 위해서는 책의 정보를 미리 설정해야 하므로 각각의 내용을 bookData를 하나 만들어서 설정해준 뒤
		 * 그 정보를 반환한다.
		 */
		return bookData;
	}

	public void refreshBookInfo(ArrayList<BookData> datas) {
		/*
		 * 검색한 책 정보를 서버로 부터 받아와서 이를 UI에 뿌려주는 메소드
		 */
		bookList.clearSelection();
		//테이블의 정보를 모두 삭제한다.
		modelBookInfo.setRowCount(0);
		Object[] rowData = new Object[ClientConstant.bookReserveInfoheader.length];
		//우선 책 테이블이 가지는 열의 수를 맞춰서 오브젝트의 길이를 결정하고 행의 수는 0으로 설정한다.
		if (datas != null) {
			//만약 받은 data 파라미터가 널이 아니라면
			for (BookData b : datas) {
				rowData[0] = b.getBookID();
				rowData[1] = b.getBookName();
				rowData[2] = b.getAuthor();
				rowData[3] = b.getPublish();
				modelBookInfo.addRow(rowData);
			}
			//for - each문을 통해 모든 데이터를 전부 책 검색 테이블에 정보를 뿌려주는 model에 받아서 애드한다.
		}
		bookList.repaint();
		scrollBookList.repaint();
		//이후 테이블을 모두 repaint하여 바뀐 정보를 갱신한다.
	}

	public void refreshBookReserveInfo(ArrayList<BookData> datas) {
		//예약 정보를 서버로 부터 받아서 UI에 뿌려주는 메소드
		//상세 구현 논리는 위의 refresh 메소드에서 전부 설명했고 이게 예약 테이블에 적용되었을 뿐임
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
		//대출 정보를 서버로 부터 받아서 UI에 뿌려주는 메소드
		//상세 구현 논리는 위의 refresh 메소드에서 전부 설명했고 이게 대출 테이블에 적용되었을 뿐임
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
	//로그아웃 패널을 위로 올려주는 메소드

}
