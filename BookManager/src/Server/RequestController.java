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
	 * Ŭ���̾�Ʈ�� ���� �Ѿ�� ��û�� ó���ϰ� Ŭ���̾�Ʈ�� ������ ������ ��Ʈ��ũ ���� �κ�
	 */
	private ServerSocket ss = null;
	//���� ������ ����
	private Socket s = null;
	//���� ������ ���� ���� ���� ������ ������ ������ ����

	ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
	//����� Ŭ���̾�Ʈ ������ ArrayList�� �ޱ� ���� ����Ʈ ������ ����

	private MessageData message = new MessageData();
	private UserData userData;
	private BookData bookData;
	private ArrayList<BookData> bookArray;
	//������ ���� ���� �޽����� �����ϰ� �� ������ ���� �ٽ� Ŭ���̾�Ʈ�� �ǵ��� �ֱ� ���� ������ �ν��Ͻ� �����͵�

	private UserInfoDAO userInfoDAO;
	private BookInfoDAO bookInfoDAO;
	//DB�� �����Ͽ� ��û�� ó���ϱ� ���� ������ DAO ������

	Logger logger;
	//�����κп��� ��ȭ�κ��� �ܼ��� ���� �˷��ִ� �α� ���� ����

	public RequestController() {
		userInfoDAO = new UserInfoDAO();
		bookInfoDAO = new BookInfoDAO();
		//�����ڰ� ��������� DAO 2���� �����Ѵ�.
	}

	public void Start() {
		//������ �����ϴ� �޼ҵ�
		logger = Logger.getLogger(this.getClass().getName());

		try {
			ss = new ServerSocket(5500);
			logger.info("Library System Server now runnig!!");
			//���� ���� ���� �� ������ ������ �����Ǿ����� �˸���.

			while (true) {
				//���� Ŭ���̾�Ʈ�� ������ �𸣹Ƿ� ���� ������ �ݺ����� ���� �д�
				s = ss.accept();
				//������������ ���� ���� ���������� ���Ͽ� ����

				ClientThread cli = new ClientThread();
				clientThreads.add(cli);
				cli.start();
				//�̋� ���� ������ ����Ǹ� Ŭ���̾�Ʈ �Ѱ��� ������ ����� ���̹Ƿ� �̸� ������� �����Ͽ� �����带 ������.

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ClientThread extends Thread {
		//Ŭ���̾�Ʈ�� ���ͼ� ������ ����ɶ� ����� �����带 ������ �̳� Ŭ����

		String msg;
		MessageData m = new MessageData();
		Gson gson = new Gson();
		//�����͸� �Ľ��ؼ� �ٽ� Ŭ���̾�Ʈ���� ������ ���� gson ������ Ŭ���̾�Ʈ�κ��� ���� ������
		//�����ܿ� �����ؼ� �����ֱ� ���� �޾ƾ� �ϴ� �޽��� ������ m �׸��� ���ڿ� msg

		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		//Ŭ���̾�Ʈ���� ������ �ް� �Ἥ ������ ���� reader�� writer ���� ����

		public void run() {
			//������ ���� �κ�
			try {
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(), true);
				int i = 0;
				//�켱 reader�� writer�� �����Ѵ�.
				//i�� ��� ArrayList�� ��� �Ǿ����� Ŭ���̾�Ʈ���� ArrayList�� ���� ������ ���� ī��Ʈ �������� �����ߴ�.
				while ((msg = inMsg.readLine()) != null) {
					//Ŭ���̾�Ʈ�κ��� ��û�� �о���µ� ���� null�� �����ٸ� ������ ������ ���̹Ƿ� ������ ����
					//�ʵ��� ����
					m = gson.fromJson(msg, MessageData.class);
					System.out.println(m.toString());
					if (m.getType().equals("LOGIN")) {
						//���� �α����� �ϱ����� ��û�� ��������
						UserData ud = m.getUserData();
						//����� ������ ���� �����صΰ�
						if (userInfoDAO.loginCheck(ud)) {
							//�α��� �������� DB�� ������ üũ�� �� �����ϴٸ�
							m.setType("LOGIN");
							// m.setUserData(m.getUserData());
							m.getUserData().setFlag(false);
							outMsg.println(gson.toJson(m));
							//�α��� �϶�� Ŭ���̾�Ʈ���� ���� �۽�
							try {
								//--> ���� Ŭ���̾�Ʈ���� �� ���̵� ������ �ִ� �뿩 ������ ���� ������
								//�����ֱ� ���� try - catch���� ����
								bookArray = bookInfoDAO.getrentAll(" where rentID = " + "\"" + ud.getUserID() + "\"");
								if (bookArray != null) {
									//--> ���� �뿩������ ���� ArrayList�� null �̸� �� ������ ���� �ʰ� ����
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
								// --> �뿩 ���� �۽� ArrayList�� ������ �ϹǷ� �ݺ����� ����߰�
								//�뿩 ������ �����ٴ°����ǹ��ϴ� ��ɾ�� Ÿ���� ������ �� �̸� Ŭ���̾�Ʈ���� ����
								//--> ���� ���� ������ �����ϴ��� Ȯ���Ͽ� Ŭ���̾�Ʈ���� ������ �۾� ����
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
								//--> �뿩������ �۽Ű� ���� ���� ����
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							//--> ���� �α��� üũ�� �α��� �Ұ����� ���¶�� ex) ��й�ȣ�� Ʋ�� ���
							m.setType("LOGIN");
							// m.setUserData(m.getUserData());
							m.getUserData().setFlag(true);
							outMsg.println(gson.toJson(m));
							//--> �α��� ���� ����� �޽����� �����Ѵ�.
						}

						// DB���� �޾ƿ� ������ ���� ȸ���� ã�� �� ID�� flag�� false�� �ٲٰ� ����
					} else if (m.getType().equals("LOGOUT")) {
						//-> �α� �ƿ��� Ŭ���̾�Ʈ�� ��û������
						if (userInfoDAO.logoutCheck(m.getUserData())) {
							m.setType("LOGOUT");
							// m.setUserData(m.getUserData());
							m.getUserData().setFlag(false);
							outMsg.println(gson.toJson(m));
						}
						// DB���� ID�� ���� ȸ���� ã�� �� flag�� true�� �ٲٰ� ����
					} else if (m.getType().equals("SEARCH")) {
						//--> å �˻��� Ŭ���̾�Ʈ�� ��û������
						if (m.getBookData().getAuthor() != null) {
							bookArray = bookInfoDAO.getbookAll("where author = " + "\"" + m.getBookData().getAuthor() + "\"");
						} else if (m.getBookData().getBookName() != null) {
							bookArray = bookInfoDAO.getbookAll("where bname = " + "\"" + m.getBookData().getBookName() + "\"");
						} else if (m.getBookData().getPublish() != null) {
							bookArray = bookInfoDAO.getbookAll("where publish = " + "\"" + m.getBookData().getPublish() + "\"");
						} else {
							bookArray = bookInfoDAO.getbookAll(null);
						}
						//--> Ŭ���̾�Ʈ�� å ������ ����� ���´����� ������ �� �� �����Ƿ� ������ ���ǵ�
						//4���� ��쿡 ���� if - else if ���� ���鼭 Ȯ���ϰ� ���ǿ� �´°��� ���� ArrayList�� �޾ƿ´�.
						if(bookArray != null) {
							//--> ���Ŀ��� �뿩���� ���������� Ŭ���̾�Ʈ���� �������� ���������� ���� ������ �����Ѵ�.
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
						// Search�ϴ� Ű���尡 ���� Ȯ���� �� �̿� ���� DB������ �޾Ƽ� Ŭ���̾�Ʈ���� �ѷ���
					} else if (m.getType().equals("RESERVE")) {
						//--> Ŭ���̾�Ʈ�� å�� ����������
						bookInfoDAO.reserveBook(m.getBookData());
						//--> DB�� ���������� ����
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
						//--> ������ ������ Ŭ���̾�Ʈ���� ������
						// Ŭ���̾�Ʈ�� ������ å�� �����ؼ� �� ������ ���� ���̺� ����� ��
					} else if (m.getType().equals("REGIST")) {
						if (userInfoDAO.insertUser(m.getUserData())) {
							//--> ȸ�� ��Ͽ� ��� DB�� ȸ�� ����� �ϸ� ���̴�.
						}
						// ȸ�����԰� ���õ� ������ ������ ȸ�� ���̺� �� ������ insert�ϰ� ����
					}
				}
			} catch (Exception e) {
				System.out.println("ClientSocket is closed!");
				//--> ������ �������� Ŭ���̾�Ʈ ������ �����ٴ� �޽����� �����ο� ����Ѵ�.
			}
		}
	}

}
