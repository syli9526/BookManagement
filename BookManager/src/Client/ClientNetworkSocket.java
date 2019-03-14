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
	 * Ŭ���̾�Ʈ�� ������ �޽����� �����ϰ� �޾ƿ��� ���� ������ �޼ҵ�
	 * Ŭ���̾�Ʈ�� ������� ���۵Ǳ� ������ Runnable�� �������̽� �����Ǿ� ����
	 */
	private String ip = "127.0.0.1";
	//���� ip�� ����� �����̹Ƿ� ���� ip�ּҸ� �̸� ����
	private Socket socket;
	//Ŭ���̾�Ʈ�� ���� ������ ����Ҷ� ����� ����
	private BufferedReader inMsg;
	//�����κ��� �޽����� �޾ƿö� ����� BufferedReader
	private PrintWriter outMsg;
	//�������Է� �޽����� �����Ҷ� ����� PrintWriter
	Gson gson = new Gson();
	//MessageData�� �Ľ��ϱ� ���� ����� �� gson ��ü
	//����� �ϱ� ���� ���ϰ� BufferedReader, PrintWriter �׸��� Message���·� ����ϱ� ���� gson�� ���� ����

	private MessageData m;
	//������ ���� �޾ƿ� ������ �����͸� ��� ������ ����
	private boolean status = true;
	//Ŭ���̾�Ʈ�� ������ ���� ������ �޾ƿö� while���� ���鼭 �����尡 ����Ǵµ� �̸� ���� ������ ���� �ϱ� ���� status ����

	private Thread thread;
	//�����带 ����� �� ����ڰ� �����ϱ� ������ �̸� ���� ������ ������ �ϳ� ����
	
	private ClientUI ui = ClientAppManager.getClinetAppManager().getClientUI();
	//������ ���� �޽����� ������ ��� view ��Ʈ�� �ݿ��Ͽ� �ѷ���� �ϹǷ� �̸� �����ϱ� ���� �۸Ŵ������
	private UserData userData;
	//����� ������ ���� �����Ͱ� �Ѿ�ö� �̸� �ޱ����� userData
	private BookData bookData;
	//å ������ ���� �����Ͱ� �Ѿ�ö� �̸� �ޱ����� bookData
	private ArrayList<BookData> bookArray;
	//å ������ �˻��� ���� �������� �޾ƾ� �Ҷ� ����� ArrayList
	
	
	public void connectServer() {
		//������ �����ϴ� �޼ҵ�
				try {
					socket = new Socket(ip, 5500);
					//�̸� ������ ip�ּҿ� ��Ʈ ��ȣ�� �̿��� ������ ����
					inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					outMsg = new PrintWriter(socket.getOutputStream(), true);
					//������ �����ø� �ְ� �������� �а� �� �� �ֵ��� �ϴ� reader�� writer�� ����
					
					thread = new Thread(this);
					thread.start();
					//�����尡 ���ƾ� �ϴ� �����κ����� �޽��� ������ ���� ������ ������ ����
				} catch (Exception e) {
					e.printStackTrace();
					//���� ���� ��Ȳ�� �߻��Ҷ�.
				}
				//���� ������ ������ ���� ����� try - catch��
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
		 * �α��� ������ ������ �����ϴ� �޼ҵ�
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
		 * �α׾ƿ� ������ ������ �����ϴ� �޼ҵ�
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
		 * å �˻� ������ ������ �����ϴ� �޼ҵ�
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
		 * ������ ������ �����ϴ� �޼ҵ�
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
		 * ȸ�������� ������ �����ϴ� �޼ҵ�
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
		 * ������ �����ߴ� ������ �ݴ� �޼ҵ�
		 */
	}
	
	public UserData getUserData() {
		return userData;
		//�� ���Ͽ��� ����� ������ �޾ƿ��� �޼ҵ�
	}
	
	public BookData getbookData() {
		return bookData;
		//�� ���Ͽ��� å ������ �޾ƿ��� �޼ҵ�
	}
	
	public void run() {
		//�����尡 ���ư����� ������ �޼ҵ�
		String msg;
		//�޽����� �Ľ��ؼ� �ٽ� ����ϱ� ���� ó�� �Է¹��� �޽����� ���ڿ��� �����ϴ� ����
		status = true;
		//�켱 �����κ����� �޽��� ������ ��� �ؾ��ϹǷ� status�� ������ ����
				while (status) {
					//�������� �Ѿ�� �����͸� Ŭ���̾�Ʈ���� ó���ϱ� ���� ����
					try {
						msg = inMsg.readLine();
						//������ ���� ���ŵ� ������ �о�´�
						m = gson.fromJson(msg, MessageData.class);
						//messageData�� �ǹ޾ƿ´�.
						System.out.println(m.toString());
						//� �����Ϳ����� �ֿܼ� ����Ѵ�. --> ���� ��� �Ѿ������ Ȯ���ϱ� ���� �̿�
						//�޽��� �����Ͱ� � ���� ó���϶�� �۽��� �޽������� �����ϱ� ���� if - else if��
						if(m.getType().equals("LOGIN")) {
							//���� �α��� ������ ó���϶�� ������
							if(!m.getUserData().isFlag()) {
								ui.setUserId(m.getUserData().getUserID());
								ui.showLogoutPanel();
								//�α����� ������ ���̶�� �α��� �� ����� ������ �гο� �ݿ��ؾ� �ϹǷ� panel�� userID��
								//�޾ƿ� �޽����� ����� ���̵�� ��ȯ�ϰ� �α��� �� ������ ȭ������ ��ȯ�Ѵ�.
							}
							else {
								JOptionPane.showMessageDialog(ui, "LoginFailed!!");
								//���� �α��ο� �����ߴٸ� ���̾�α׸� ��� �α����� �����ߴٰ� �����Ѵ�.
							}
						}
						//�α��� ����
						else if(m.getType().equals("LOGOUT")) {
							//���� �α׾ƿ��� ���� ó���� ��û������
							if(!m.getUserData().isFlag()) {
								ui.setUserId("");
								ui.showLoginPanel();
								ui.refreshBookRentInfo(null);
								ui.refreshBookReserveInfo(null);
								//�гο� ���̵� �������� ��ġ�� ���������� ���� ������ ��� ������� ���� ��
								//�α��� ȭ���� ����ش�.
							}
						}
						//�α׾ƿ� ����
						else if(m.getType().equals("SEARCHSTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//�˻������Ͱ� ó������ �޾����� �̸� �ݿ���
						}
						//�˻��� ������ ����
						else if(m.getType().equals("SEARCH")) {
							bookArray.add(m.getBookData());
							//�˻������͸� �޾ƿ�
						}
						//�˻����� �޴���
						else if(m.getType().equals("SEARCHEND")) {
							ui.refreshBookInfo(bookArray);
							//�˻� �����Ͱ� ������ �̸� �ݿ���
						}
						//�˻� ������ ������
						/*
						 * SEARCH���� ��ɵ��� �˻��� ������ �������϶� ���� �޾ƿ��°��� �ϳ��� bookData���� �����Ƿ�
						 * �̸� ArrayList�� �޾ƿ��� ���ؼ� ���� - ������ - �������� �����ϴ� ������� ������ ������
						 * �Ʒ��� �����ϴ� ��� START - END �� ���еǴ� ��ɵ��� �� ������ ó���ϱ� ���� �������
						 * �κ���
						 */
						else if(m.getType().equals("RESERVESTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//���� �����Ͱ� ó������ �޾����� �̸� �ݿ���
						}
						//���� ������ ����
						else if(m.getType().equals("RESERVE")) {
							bookArray.add(m.getBookData());
							//���������� �޾ƿ�
						}
						//�������� �޴���
						else if(m.getType().equals("RESERVEEND")) {
							//bookArray.add(m.getBookData());
							ui.refreshBookReserveInfo(bookArray);
							//���� ������ �����°��� �������� �ݿ�
						}
						//���� ������ ������
						/*
						 * RESERVE���� ��ɵ��� �˻��� ������ �������϶� ���� �޾ƿ��°��� �ϳ��� bookData���� �����Ƿ�
						 * �̸� ArrayList�� �޾ƿ��� ���ؼ� ���� - ������ - �������� �����ϴ� ������� ������ ������
						 * �Ʒ��� �����ϴ� ��� START - END �� ���еǴ� ��ɵ��� �� ������ ó���ϱ� ���� �������
						 * �κ���
						 */
						else if(m.getType().equals("REGIST")) {
							JOptionPane.showMessageDialog(null, "Regist is done.");
						}
						//ȸ�� ���
						else if(m.getType().equals("RENTINFOSTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//���� �����Ͱ� ó������ �޾����� �̸� �ݿ���
						}
						//�α��� �Ҷ� ���� ������ ����
						else if(m.getType().equals("RENTINFO")) {
							bookArray.add(m.getBookData());
							//�α��� �Ҷ� ���� ������ ���� �޾ƿ� --> ���⼭ ó��
						}
						//�α��� �ҋ� ���� ���� �޴���
						else if(m.getType().equals("RENTINFOEND")) {
							//bookArray.add(m.getBookData());
							for(BookData d : bookArray) {
								System.out.println(d.toString());
							}
							ui.refreshBookRentInfo(bookArray);
							//���������� �������϶�...
						}
						//�α��� �Ҷ� ���� ������ ������
						/*
						 * �α��� �������� ��������
						 * �� ��ɵ��� �˻��� ������ �������϶� ���� �޾ƿ��°��� �ϳ��� bookData���� �����Ƿ�
						 * �̸� ArrayList�� �޾ƿ��� ���ؼ� ���� - ������ - �������� �����ϴ� ������� ������ ������
						 * �Ʒ��� �����ϴ� ��� START - END �� ���еǴ� ��ɵ��� �� ������ ó���ϱ� ���� �������
						 * �κ���
						 */
						else if(m.getType().equals("RESERVEINFOSTART")) {
							bookArray = new ArrayList<BookData>();
							bookArray.add(m.getBookData());
							//���� �����Ͱ� ó������ �޾����� �̸� �ݿ���
						}
						//�α��� �ҋ� ���� ������ ����
						else if(m.getType().equals("RESERVEDINFO")) {
							bookArray.add(m.getBookData());
							//�α��� �Ҷ� ���� ������ ���� �޾ƿ� --> ���⼭ ó��
						}
						//�α��� �ҋ� ���� ���� �޴���
						else if(m.getType().equals("RESERVEDINFOEND")) {
							//bookArray.add(m.getBookData());
							for(BookData d : bookArray) {
								System.out.println(d.toString());
							}
							ui.refreshBookReserveInfo(bookArray);
							//���������� �������϶�...
						}
						//�α��� �Ҷ� ���� ������ ������
						/*
						 * �α��� �������� ��������
						 * �� ��ɵ��� �˻��� ������ �������϶� ���� �޾ƿ��°��� �ϳ��� bookData���� �����Ƿ�
						 * �̸� ArrayList�� �޾ƿ��� ���ؼ� ���� - ������ - �������� �����ϴ� ������� ������ ������
						 * �Ʒ��� �����ϴ� ��� START - END �� ���еǴ� ��ɵ��� �� ������ ó���ϱ� ���� �������
						 * �κ���
						 */
					}
					catch(SocketException se) {
						System.out.println("ServerSocket is closed!");
						status = false;
						close();
						//���� ������ ���� ����� �������� ���ܻ�Ȳ�� �߻��ߴٸ� ���� ������ �����ٴ� �޽����� �ֿܼ� ����ϰ�
						//������ �ݾƹ����� ���� ������ Ǯ��� �ϹǷ� status�� false�� �ٲ۴�.
					}
					catch(IOException e) {
						e.printStackTrace();
						status = false;
						break;
					}
				}
	}
	
}
