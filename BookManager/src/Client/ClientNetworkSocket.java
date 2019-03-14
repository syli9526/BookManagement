package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.logging.*;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import ClientUI.ClientUI;
import DataStructure.*;

public class ClientNetworkSocket implements Runnable{

	/*
	 * 클라이언트가 서버로 메시지를 전달하고 받아오기 위해 구현된 메소드
	 * 클라이언트가 스레드로 동작되기 때문에 Runnable로 인터페이스 구현되어 있음
	 */
	private String ip = "127.0.0.1";
	//로컬 ip를 사용할 예정이므로 로컬 ip주소를 미리 정의
	private Socket socket;
	//클라이언트가 만들어서 서버와 통신할때 사용할 소켓
	private BufferedReader inMsg;
	//서버로부터 메시지를 받아올때 사용할 BufferedReader
	private PrintWriter outMsg;
	//서버에게로 메시지를 전달할때 사용할 PrintWriter
	Gson gson = new Gson();
	//MessageData를 파싱하기 위해 만들어 둔 gson 객체
	//통신을 하기 위한 소켓과 BufferedReader, PrintWriter 그리고 Message형태로 통신하기 위한 gson을 각각 선언

	private MessageData m;
	//서버로 부터 받아올 메지시 데이터를 잠시 저장할 변수
	private boolean status = true;
	//클라이언트가 서버로 부터 정보를 받아올때 while문을 돌면서 스레드가 진행되는데 이를 무한 루프로 돌게 하기 위한 status 변수

	private Thread thread;
	//스레드를 사용해 각 사용자가 접근하기 때문에 이를 위한 스레드 변수를 하나 선언
	
	private ClientUI ui = ClientAppManager.getClinetAppManager().getClientUI();
	//소켓을 통해 메시지를 받으면 즉시 view 파트에 반영하여 뿌려줘야 하므로 이를 실현하기 위한 앱매니저사용
	private UserData userData;
	//사용자 정보에 관한 데이터가 넘어올때 이를 받기위한 userData
	private BookData bookData;
	//책 정보에 대한 데이터가 넘어올때 이를 받기위한 bookData
	private ArrayList<BookData> bookArray;
	//책 정보가 검색을 통해 여러개를 받아야 할때 사용할 ArrayList
	
	
	public void connectServer() {
		//서버에 연결하는 메소드
				try {
					socket = new Socket(ip, 5500);
					//미리 설정한 ip주소와 포트 번호를 이용해 소켓을 생성
					inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					outMsg = new PrintWriter(socket.getOutputStream(), true);
					//서버와 메지시를 주고 받은것을 읽고 쓸 수 있도록 하는 reader와 writer를 생성
					
					thread = new Thread(this);
					thread.start();
					//스레드가 돌아야 하는 서버로부터의 메시지 수신을 위한 스레드 생성과 실행
				} catch (Exception e) {
					e.printStackTrace();
					//연결 예외 상황이 발생할때.
				}
				//소켓 연결이 실패할 때를 대비한 try - catch문
	}
	
	public boolean sendToLoginMessage(MessageData message) {
		try {
			outMsg.println(gson.toJson(message));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		/*
		 * 로그인 정보를 서버로 전송하는 메소드
		 */
	}
	
	public boolean sendToLogoutMessage(MessageData message) {
		try {
			outMsg.println(gson.toJson(message));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		/*
		 * 로그아웃 정보를 서버로 전달하는 메소드
		 */
	}
	
	public boolean sendToSearchMessage(MessageData message) {
		try {
			outMsg.println(gson.toJson(message));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		/*
		 * 책 검색 정보를 서버로 전달하는 메소드
		 */
	}
	
	public boolean sendToReserveMessage(MessageData message) {
		try {
			outMsg.println(gson.toJson(message));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		/*
		 * 예약을 서버로 전달하는 메소드
		 */
	}
	
	public boolean sendToRegistMessage(MessageData message) {
		try {
			outMsg.println(gson.toJson(message));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		/*
		 * 회원가입을 서버로 전달하는 메소드
		 */
	}
	
	public void close() {
		try{
			socket.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		/*
		 * 서버로 연결했던 소켓을 닫는 메소드
		 */
	}
	
	public UserData getUserData() {
		return userData;
		//이 소켓에서 사용자 정보를 받아오는 메소드
	}
	
	public BookData getbookData() {
		return bookData;
		//이 소켓에서 책 정보를 받아오는 메소드
	}
	
	public void run() {
		//스레드가 돌아갈때를 구현한 메소드
		String msg;
		//메시지를 파싱해서 다시 사용하기 전에 처음 입력받은 메시지를 문자열로 저장하는 변수
		status = true;
		//우선 서버로부터의 메시지 수신은 계속 해야하므로 status는 참으로 시작
				while (status) {
					//서버에서 넘어온 데이터를 클라이언트에서 처리하기 위해 실행
					try {
						msg = inMsg.readLine();
						//서버로 부터 수신된 정보를 읽어온다
						m = gson.fromJson(msg, MessageData.class);
						//messageData로 되받아온다.
						System.out.println(m.toString());
						//어떤 데이터였는지 콘솔에 출력한다. --> 실제 어떻게 넘어오는지 확인하기 위해 이용
						//메시지 데이터가 어떤 일을 처리하라고 송신한 메시지인지 구분하기 위한 if - else if문
						if(m.getType().equals("LOGIN")) {
							//만약 로그인 정보를 처리하라고 했을때
							if(!m.getUserData().isFlag()) {
								ui.setUserId(m.getUserData().getUserID());
								ui.showLogoutPanel();
								//로그인이 성공한 것이라면 로그인 한 사용자 정보를 패널에 반영해야 하므로 panel의 userID를
								//받아온 메시지의 사용자 아이디로 전환하고 로그인 한 이후의 화면으로 전환한다.
							}
							else {
								JOptionPane.showMessageDialog(ui, "LoginFailed!!");
								//만약 로그인에 실패했다면 다이얼로그를 띄워 로그인이 실패했다고 전달한다.
							}
						}
						//로그인 정보
						else if(m.getType().equals("LOGOUT")) {
							//만약 로그아웃에 대한 처리를 요청했을때
							if(!m.getUserData().isFlag()) {
								ui.setUserId("");
								ui.showLoginPanel();
								ui.refreshBookRentInfo(null);
								ui.refreshBookReserveInfo(null);
								//패널에 아이디를 없음으로 고치고 대출정보와 예약 정보를 모두 사라지게 만든 후
								//로그인 화면을 띄워준다.
							}
						}
						//로그아웃 정보
						else if(m.getType().equals("SEARCHSTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//검색데이터가 처음으로 받아질때 이를 반영함
						}
						//검색한 정보의 시작
						else if(m.getType().equals("SEARCH")) {
							bookArray.add(m.getBookData());
							//검색데이터를 받아옴
						}
						//검색정보 받는중
						else if(m.getType().equals("SEARCHEND")) {
							ui.refreshBookInfo(bookArray);
							//검색 데이터가 끝날때 이를 반영함
						}
						//검색 정보의 마지막
						/*
						 * SEARCH류의 명령들은 검색한 정보가 여러개일때 실제 받아오는것은 하나의 bookData씩만 들어오므로
						 * 이를 ArrayList로 받아오기 위해서 시작 - 진행중 - 마지막을 구분하는 명령으로 분해해 구현함
						 * 아래에 존재하는 모든 START - END 로 구분되는 명령들은 이 과정을 처리하기 위해 만들어진
						 * 부분임
						 */
						else if(m.getType().equals("RESERVESTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//예약 데이터가 처음으로 받아질때 이를 반영함
						}
						//예약 정보의 시작
						else if(m.getType().equals("RESERVE")) {
							bookArray.add(m.getBookData());
							//예약정보를 받아옴
						}
						//예약정보 받는중
						else if(m.getType().equals("RESERVEEND")) {
							//bookArray.add(m.getBookData());
							ui.refreshBookReserveInfo(bookArray);
							//예약 정보가 들어오는것이 끝날때를 반영
						}
						//예약 정보의 마지막
						/*
						 * RESERVE류의 명령들은 검색한 정보가 여러개일때 실제 받아오는것은 하나의 bookData씩만 들어오므로
						 * 이를 ArrayList로 받아오기 위해서 시작 - 진행중 - 마지막을 구분하는 명령으로 분해해 구현함
						 * 아래에 존재하는 모든 START - END 로 구분되는 명령들은 이 과정을 처리하기 위해 만들어진
						 * 부분임
						 */
						else if(m.getType().equals("REGIST")) {
							JOptionPane.showMessageDialog(null, "Regist is done.");
						}
						//회원 등록
						else if(m.getType().equals("RENTINFOSTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//대출 데이터가 처음으로 받아질때 이를 반영함
						}
						//로그인 할때 대출 정보의 시작
						else if(m.getType().equals("RENTINFO")) {
							bookArray.add(m.getBookData());
							//로그인 할때 대출 정보도 같이 받아옴 --> 여기서 처리
						}
						//로그인 할떄 대출 정보 받는중
						else if(m.getType().equals("RENTINFOEND")) {
							//bookArray.add(m.getBookData());
							for(BookData d : bookArray) {
								System.out.println(d.toString());
							}
							ui.refreshBookRentInfo(bookArray);
							//대출정보가 마지막일때...
						}
						//로그인 할때 대출 정보의 마지막
						/*
						 * 로그인 했을때의 대출정보
						 * 이 명령들은 검색한 정보가 여러개일때 실제 받아오는것은 하나의 bookData씩만 들어오므로
						 * 이를 ArrayList로 받아오기 위해서 시작 - 진행중 - 마지막을 구분하는 명령으로 분해해 구현함
						 * 아래에 존재하는 모든 START - END 로 구분되는 명령들은 이 과정을 처리하기 위해 만들어진
						 * 부분임
						 */
						else if(m.getType().equals("RESERVEINFOSTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//예약 데이터가 처음으로 받아질때 이를 반영함
						}
						//로그인 할떄 예약 정보의 시작
						else if(m.getType().equals("RESERVEDINFO")) {
							bookArray.add(m.getBookData());
							//로그인 할때 예약 정보도 같이 받아옴 --> 여기서 처리
						}
						//로그인 할떄 예약 정보 받는중
						else if(m.getType().equals("RESERVEDINFOEND")) {
							//bookArray.add(m.getBookData());
							for(BookData d : bookArray) {
								System.out.println(d.toString());
							}
							ui.refreshBookReserveInfo(bookArray);
							//예약정보가 마지막일때...
						}
						//로그인 할때 예약 정보의 마지막
						/*
						 * 로그인 했을때의 예약정보
						 * 이 명령들은 검색한 정보가 여러개일때 실제 받아오는것은 하나의 bookData씩만 들어오므로
						 * 이를 ArrayList로 받아오기 위해서 시작 - 진행중 - 마지막을 구분하는 명령으로 분해해 구현함
						 * 아래에 존재하는 모든 START - END 로 구분되는 명령들은 이 과정을 처리하기 위해 만들어진
						 * 부분임
						 */
					}
					catch(SocketException se) {
						System.out.println("ServerSocket is closed!");
						status = false;
						close();
						//만약 서버로 부터 통신이 끊어지는 예외상황이 발생했다면 서버 소켓은 닫혔다는 메시지를 콘솔에 출력하고
						//소켓을 닫아버리며 무한 루프를 풀어야 하므로 status를 false로 바꾼다.
					}
					catch(IOException e) {
						e.printStackTrace();
						status = false;
						break;
					}
				}
	}
	
}
