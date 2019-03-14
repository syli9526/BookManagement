package Client;

import ClientUI.ClientUI;
import DataStructure.*;
import Server.ServerConstant;

import java.awt.event.*;

import javax.swing.JOptionPane;

public class ClientActionListener implements ActionListener {
	/*
	 * 		클라이언트 부분에서 버튼을 클릭했을때 일어나는 이벤트 핸들링을 위한 클래스
	 */
	private ClientUI ui = ClientAppManager.getClinetAppManager().getClientUI();
	//싱글톤 패턴을 적용한 ui를 받아오는 객체
	private UserData userData;
	//이용자 정보를 메시지로 패키징 할때 사용하는 변수
	private BookData bookData;
	//책 정보를 메시지로 패키징 할때 사용하는 변수
	private MessageData message;
	//실제 gson을 통해 메시지를 전송할때 사용할 메시지 포멧 변수
	private ClientNetworkSocket socket;
	//네트워크 연결을 하여 서버로 통신하므로 이를 구현하기 위한 socket 변수

	public ClientActionListener() {
		socket = new ClientNetworkSocket();
		socket.connectServer();
	}
	//이벤트 리스너를 생성할때 소켓을 열어놓고 서버와 연결한다. ClientNetworkSocket은 소켓 연결을 위해 따로 구현한 클래스이다.

	public void actionPerformed(ActionEvent e) {
		//이벤트 핸들링 부분
		Object obj  = e.getSource();
		if(obj == ui.getbtnLogin()) {
			//로그인
			ui.setUserId(ui.loginID());
			System.out.println(ui.loginPW());
			userData = new UserData();
			userData.setUserID(ui.loginID());
			userData.setUserPassword(ui.loginPW());
			userData.setFlag(true);
			message = new MessageData();
			message.setUserData(userData);
			message.setType(ClientConstant.LOGIN);
			socket.sendToLoginMessage(message);
			/*
			 * 로그인 할때를 구현한 부분 이 부분에서는 메시지로 보낼 부분이 사용자 아이디와 비밀번호 뿐이므로 이를 
			 * userData에 담은 후 메지시데이터로 만들어 socket을 생성하고 기능을 정의한 ClientNetworkSocket클래스에
			 * 구현된 sendToLoginMessage 메소드를 통해 보낸다.
			 */
		}
		else if(obj == ui.getbtnLogout()) {
			//로그아웃
			userData = new UserData();
			userData.setUserID(ui.loginID());
			userData.setFlag(false);
			message = new MessageData();
			message.setUserData(userData);
			message.setType(ClientConstant.LOGOUT);
			if(socket.sendToLogoutMessage(message)) { // true --> network communication success is need
				System.out.println("로그아웃 합니다.");
				ui.showLoginPanel();
			}
			/*
			 * 로그아웃을 할때 구현한 부분이다. 이 부분에서는 메지시로 보낼 부분이 사용자의 아이디와 로그인 중인 여부를 false로 
			 * 바꿔달라는 요청 뿐이므로 이 데이터만 담아 메시지로 감싼다음 보낸다. 모든게 완료되면 로그인 하는 화면으로 전환한다.
			 */
		}
		else if(obj == ui.getbtnSearch()) {
			//검색
			bookData = ui.getSearchData();
			message = new MessageData();
			message.setBookData(bookData);
			message.setType(ClientConstant.SEARCH);
			socket.sendToSearchMessage(message);
			System.out.println("책을 검색합니다.");
			/*
			 * 책을 검색하는 부분 책을 검색할 때에는 책에 관한 정보중 키워드를 담아서 보내면 되므로 이를 view 파트에서 데이터를
			 * 담아 두는 메소드를 따로 만들었으므로 이를 불러와서 정보를 받은 후 메시지로 패키징 해서 서버로 전달한다.
			 */
		}
		else if(obj == ui.getbtnReserve()) {
			//예약
			bookData = ui.getReservedData();
			System.out.println(bookData.toString());
			bookData.setRentUserID(ui.loginID());
			userData = new UserData();
			userData.setUserID(ui.loginID());
			message = new MessageData();
			message.setBookData(bookData);
			message.setUserData(userData);
			message.setType(ClientConstant.RESERVE);
			int option = JOptionPane.showConfirmDialog(ui, "If you Click the OK button you cannot cancel the reservation!");
			if(option == JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(ui, "The reservation infomation can check logout and login again.");
				socket.sendToReserveMessage(message);
				System.out.println("책을 예약합니다.");
			}
			else {
				JOptionPane.showMessageDialog(ui, "Cancel the reservation");
			}
			/*
			 * 책을 예약하는 부분 내가 책을 검색한 후 선택한 책을 예약하기를 누르면 책의 정보와 사용자의 아이디를 받아서 메시지로
			 * 패키징 한 후 정보를 서버에게 전달한다. 이때 예약을 취소하고 싶으면 취소 or 아니오 를 누르면 취소되었다는 정보가
			 * 확인 창으로 뜨며 예약을 정말 한다면 예약 정보는 로그아웃 한 후 다시 로그인 해야 함을 고지한다.
			 */
		}
		else if(obj == ui.getbtnRegist()) {
			//회원가입
			ui.createRegistPane();
			userData = new UserData();
			userData.setUserID(ui.getRegID());
			userData.setUserPassword(ui.getRegPW());
			userData.setUserName(ui.getRegName());
			userData.setFlag(false);
			message = new MessageData();
			message.setUserData(userData);
			message.setType(ClientConstant.REGIST);
			if(socket.sendToRegistMessage(message)) {
				JOptionPane.showMessageDialog(null, "Regist Success!");
			}
			System.out.println("회원 가입합니다.");
			/*
			 * 회원 가입을 하기 위해 버튼을 눌렀을때 구현 이떄에는 view 파트에 구현해 둔 사용자 정의 다이얼로그에 받은
			 * 등록하고 싶은 아이디와 비밀번호 그리고 사용자 이름을 적어서 보내주면 이를 패키징해서 서보로 보내준다.
			 */
		}
		else if(obj == ui.getbtnExit()) {
			//종료
			System.out.println("프로그램을 종료합니다.");
			userData = new UserData();
			userData.setUserID(ui.loginID());
			userData.setFlag(false);
			message = new MessageData();
			message.setUserData(userData);
			message.setType(ClientConstant.LOGOUT);
			if(socket.sendToLogoutMessage(message)) { // true --> network communication success is need
				System.out.println("로그아웃 합니다.");
				ui.showLoginPanel();
			}
			socket.close();
			
			System.exit(0);
			/*
			 * 프로그램 종료버튼을 눌렀을때 구현
			 * 로그인을 한 상태에서만 보이는 버튼이므로 종료버튼을 눌렀다면 로그아웃도 같이 해줘야 하므로 로그아웃 하겠다는
			 * 메시지를 서버에 전달하는 루틴을 로그아웃하는 버튼의 구현부에서 가져와서 먼저 사용한 다음 소켓을 닫아 서버가 죽는것을
			 * 미리 방지하고 클라이언트 부분 프로그램을 종료한다.
			 */
		}
	}

}
