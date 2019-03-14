package Server;

import DataStructure.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import DAO.*;

public class RequestController {

	/*
	 * 클라이언트로 부터 넘어온 요청을 처리하고 클라이언트로 보내는 서버의 네트워크 연결 부분
	 */
	private ServerSocket ss = null;
	//서버 소켓을 선언
	private Socket s = null;
	//서버 소켓을 통해 받은 소켓 정보를 저장할 소켓을 선언

	ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
	//연결된 클라이언트 정보를 ArrayList로 받기 위한 리스트 선언후 생성

	private MessageData message = new MessageData();
	private UserData userData;
	private BookData bookData;
	private ArrayList<BookData> bookArray;
	//소켓을 통해 받은 메시지를 저장하고 이 정보를 통해 다시 클라이언트로 되돌려 주기 위해 선언한 인스턴스 데이터들

	private UserInfoDAO userInfoDAO;
	private BookInfoDAO bookInfoDAO;
	//DB에 연결하여 요청을 처리하기 위해 선언한 DAO 변수들

	Logger logger;
	//서버부분에서 변화부분을 콘솔을 통해 알려주는 로그 정보 변수

	public RequestController() {
		userInfoDAO = new UserInfoDAO();
		bookInfoDAO = new BookInfoDAO();
		//생성자가 만들어질때 DAO 2개를 생성한다.
	}

	public void Start() {
		//서버가 시작하는 메소드
		logger = Logger.getLogger(this.getClass().getName());

		try {
			ss = new ServerSocket(5500);
			logger.info("Library System Server now runnig!!");
			//서버 소켓 생성 후 도서관 서버가 가동되었음을 알린다.

			while (true) {
				//언제 클라이언트가 들어올지 모르므로 무한 루프로 반복문을 열어 둔다
				s = ss.accept();
				//서버소켓으로 부터 받은 소켓정보를 소켓에 저장

				ClientThread cli = new ClientThread();
				clientThreads.add(cli);
				cli.start();
				//이떄 위의 과정이 통과되면 클라이언트 한개가 서버로 연결된 것이므로 이를 스레드로 생성하여 스레드를 돌린다.

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ClientThread extends Thread {
		//클라이언트가 들어와서 서버와 연결될때 실행될 스레드를 구현한 이너 클래스

		String msg;
		MessageData m = new MessageData();
		Gson gson = new Gson();
		//데이터를 파싱해서 다시 클라이언트에게 보내기 위한 gson 변수와 클라이언트로부터 받은 정보를
		//서버단에 번역해서 보여주기 위해 받아야 하는 메시지 데이터 m 그리고 문자열 msg

		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		//클라이언트에게 정보를 받고 써서 보내기 위한 reader와 writer 변수 선언

		public void run() {
			//스레드 실행 부분
			try {
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(), true);
				int i = 0;
				//우선 reader와 writer를 생성한다.
				//i의 경우 ArrayList를 얻게 되었을때 클라이언트에게 ArrayList를 전부 보내기 위한 카운트 개념으로 선언했다.
				while ((msg = inMsg.readLine()) != null) {
					//클라이언트로부터 요청을 읽어오는데 만약 null을 보낸다면 연결이 끊어진 것이므로 루프를 돌지
					//않도록 설정
					m = gson.fromJson(msg, MessageData.class);
					System.out.println(m.toString());
					if (m.getType().equals("LOGIN")) {
						//만약 로그인을 하기위한 요청이 들어왔을때
						UserData ud = m.getUserData();
						//사용자 정보를 따로 저장해두고
						if (userInfoDAO.loginCheck(ud)) {
							//로그인 가능한지 DB에 연결해 체크한 후 가능하다면
							m.setType("LOGIN");
							// m.setUserData(m.getUserData());
							m.getUserData().setFlag(false);
							outMsg.println(gson.toJson(m));
							//로그인 하라고 클라이언트에게 정보 송신
							try {
								//--> 이후 클라이언트에게 이 아이디가 가지고 있는 대여 정보와 예약 정보를
								//보여주기 위한 try - catch문을 실행
								bookArray = bookInfoDAO.getrentAll(" where rentID = " + "\"" + ud.getUserID() + "\"");
								if (bookArray != null) {
									//--> 만약 대여정보에 대한 ArrayList가 null 이면 이 루프를 돌지 않게 설정
									for (i = 0; i < bookArray.size(); i++) {
										m.setBookData(bookArray.get(i).getBookData());
										System.out.println(m.toString());
										if (i == 0) {
											m.setType("RENTINFOSTART");
											outMsg.println(gson.toJson(m));
										} else {
											m.setType("RENTINFO");
											outMsg.println(gson.toJson(m));
										}
									}
									m.setType("RENTINFOEND");
									outMsg.println(gson.toJson(m));
								}
								// --> 대여 정보 송신 ArrayList를 보내야 하므로 반복문을 사용했고
								//대여 정보를 보낸다는것을의미하는 명령어로 타입을 변경한 후 이를 클라이언트에게 전송
								//--> 이후 예약 정보가 존재하는지 확인하여 클라이언트에게 보내는 작업 시행
								bookArray = bookInfoDAO.getLoginReservedData(" where reservationID = " + "\"" + ud.getUserID() + "\"");
								if(bookArray != null) {
									for (i = 0; i < bookArray.size(); i++) {
										m.setBookData(bookArray.get(i).getBookData());
										System.out.println(bookArray.get(i).toString());
										if (i == 0) {
											m.setType("RESERVEINFOSTART");
											outMsg.println(gson.toJson(m));
										} else {
											m.setType("RESERVEDINFO");
											outMsg.println(gson.toJson(m));
										}
									}
									m.setType("RESERVEDINFOEND");
									outMsg.println(gson.toJson(m));
								}
								//--> 대여정보의 송신과 같은 논리로 시행
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							//--> 만약 로그인 체크시 로그인 불가능한 상태라면 ex) 비밀번호가 틀린 경우
							m.setType("LOGIN");
							// m.setUserData(m.getUserData());
							m.getUserData().setFlag(true);
							outMsg.println(gson.toJson(m));
							//--> 로그인 하지 말라는 메시지를 전달한다.
						}

						// DB에서 받아온 정보를 통해 회원을 찾은 후 ID와 flag를 false로 바꾸고 종료
					} else if (m.getType().equals("LOGOUT")) {
						//-> 로그 아웃을 클라이언트가 요청했을때
						if (userInfoDAO.logoutCheck(m.getUserData())) {
							m.setType("LOGOUT");
							// m.setUserData(m.getUserData());
							m.getUserData().setFlag(false);
							outMsg.println(gson.toJson(m));
						}
						// DB에서 ID를 통해 회원을 찾은 후 flag를 true로 바꾸고 종료
					} else if (m.getType().equals("SEARCH")) {
						//--> 책 검색을 클라이언트가 요청했을때
						if (m.getBookData().getAuthor() != null) {
							bookArray = bookInfoDAO.getbookAll("where author = " + "\"" + m.getBookData().getAuthor() + "\"");
						} else if (m.getBookData().getBookName() != null) {
							bookArray = bookInfoDAO.getbookAll("where bname = " + "\"" + m.getBookData().getBookName() + "\"");
						} else if (m.getBookData().getPublish() != null) {
							bookArray = bookInfoDAO.getbookAll("where publish = " + "\"" + m.getBookData().getPublish() + "\"");
						} else {
							bookArray = bookInfoDAO.getbookAll(null);
						}
						//--> 클라이언트가 책 정보중 어떤것을 보냈는지는 서버가 알 수 없으므로 사전에 협의된
						//4가지 경우에 따른 if - else if 문을 돌면서 확인하고 조건에 맞는것을 통해 ArrayList를 받아온다.
						if(bookArray != null) {
							//--> 이후에는 대여정보 예약정보를 클라이언트에게 보낼때와 마찬가지의 논리로 정보를 전달한다.
							for (i = 0; i < bookArray.size(); i++) {
								m.setBookData(bookArray.get(i).getBookData());
								if (i == 0) {
									m.setType("SEARCHSTART");
									outMsg.println(gson.toJson(m));
								} else {
									m.setType("SEARCH");
									outMsg.println(gson.toJson(m));
								}
							}
							m.setType("SEARCHEND");
							outMsg.println(gson.toJson(m));
						}
						// Search하는 키워드가 뭔지 확인한 후 이에 따른 DB정보를 받아서 클라이언트에게 뿌려줌
					} else if (m.getType().equals("RESERVE")) {
						//--> 클라이언트가 책을 예약했을때
						bookInfoDAO.reserveBook(m.getBookData());
						//--> DB에 예약정보를 저장
						bookArray = bookInfoDAO.getLoginReservedData("where reservationID = " + "\"" + m.getUserData().getUserID() + "\"");
						if(bookArray != null) {
							for (i = 0; i < bookArray.size(); i++) {
								m.setBookData(bookArray.get(i).getBookData());
								if (i == 0) {
									m.setType("RESERVESTART");
									outMsg.println(gson.toJson(m));
								} else {
									m.setType("RESERVE");
									outMsg.println(gson.toJson(m));
								}
							}
							m.setType("RESERVEEND");
							outMsg.println(gson.toJson(m));
						}
						//--> 예약한 내용을 클라이언트에게 전송함
						// 클라이언트가 선택한 책을 결정해서 이 내용을 예약 테이블에 등록한 후
					} else if (m.getType().equals("REGIST")) {
						if (userInfoDAO.insertUser(m.getUserData())) {
							//--> 회원 등록에 경우 DB에 회원 등록을 하면 끝이다.
						}
						// 회원가입과 관련된 정보를 보내서 회원 테이블에 이 내용을 insert하고 종료
					}
				}
			} catch (Exception e) {
				System.out.println("ClientSocket is closed!");
				//--> 연결이 끊겼을때 클라이언트 소켓이 닫혔다는 메시지를 서버부에 출력한다.
			}
		}
	}

}
