package Client;

import ClientUI.ClientUI;
import DataStructure.*;
import Server.ServerConstant;

import java.awt.event.*;

import javax.swing.JOptionPane;

public class ClientActionListener implements ActionListener {
	/*
	 * 		Ŭ���̾�Ʈ �κп��� ��ư�� Ŭ�������� �Ͼ�� �̺�Ʈ �ڵ鸵�� ���� Ŭ����
	 */
	private ClientUI ui = ClientAppManager.getClinetAppManager().getClientUI();
	//�̱��� ������ ������ ui�� �޾ƿ��� ��ü
	private UserData userData;
	//�̿��� ������ �޽����� ��Ű¡ �Ҷ� ����ϴ� ����
	private BookData bookData;
	//å ������ �޽����� ��Ű¡ �Ҷ� ����ϴ� ����
	private MessageData message;
	//���� gson�� ���� �޽����� �����Ҷ� ����� �޽��� ���� ����
	private ClientNetworkSocket socket;
	//��Ʈ��ũ ������ �Ͽ� ������ ����ϹǷ� �̸� �����ϱ� ���� socket ����

	public ClientActionListener() {
		socket = new ClientNetworkSocket();
		socket.connectServer();
	}
	//�̺�Ʈ �����ʸ� �����Ҷ� ������ ������� ������ �����Ѵ�. ClientNetworkSocket�� ���� ������ ���� ���� ������ Ŭ�����̴�.

	public void actionPerformed(ActionEvent e) {
		//�̺�Ʈ �ڵ鸵 �κ�
		Object obj  = e.getSource();
		if(obj == ui.getbtnLogin()) {
			//�α���
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
			 * �α��� �Ҷ��� ������ �κ� �� �κп����� �޽����� ���� �κ��� ����� ���̵�� ��й�ȣ ���̹Ƿ� �̸� 
			 * userData�� ���� �� �����õ����ͷ� ����� socket�� �����ϰ� ����� ������ ClientNetworkSocketŬ������
			 * ������ sendToLoginMessage �޼ҵ带 ���� ������.
			 */
		}
		else if(obj == ui.getbtnLogout()) {
			//�α׾ƿ�
			userData = new UserData();
			userData.setUserID(ui.loginID());
			userData.setFlag(false);
			message = new MessageData();
			message.setUserData(userData);
			message.setType(ClientConstant.LOGOUT);
			if(socket.sendToLogoutMessage(message)) { // true --> network communication success is need
				System.out.println("�α׾ƿ� �մϴ�.");
				ui.showLoginPanel();
			}
			/*
			 * �α׾ƿ��� �Ҷ� ������ �κ��̴�. �� �κп����� �����÷� ���� �κ��� ������� ���̵�� �α��� ���� ���θ� false�� 
			 * �ٲ�޶�� ��û ���̹Ƿ� �� �����͸� ��� �޽����� ���Ѵ��� ������. ���� �Ϸ�Ǹ� �α��� �ϴ� ȭ������ ��ȯ�Ѵ�.
			 */
		}
		else if(obj == ui.getbtnSearch()) {
			//�˻�
			bookData = ui.getSearchData();
			message = new MessageData();
			message.setBookData(bookData);
			message.setType(ClientConstant.SEARCH);
			socket.sendToSearchMessage(message);
			System.out.println("å�� �˻��մϴ�.");
			/*
			 * å�� �˻��ϴ� �κ� å�� �˻��� ������ å�� ���� ������ Ű���带 ��Ƽ� ������ �ǹǷ� �̸� view ��Ʈ���� �����͸�
			 * ��� �δ� �޼ҵ带 ���� ��������Ƿ� �̸� �ҷ��ͼ� ������ ���� �� �޽����� ��Ű¡ �ؼ� ������ �����Ѵ�.
			 */
		}
		else if(obj == ui.getbtnReserve()) {
			//����
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
				System.out.println("å�� �����մϴ�.");
			}
			else {
				JOptionPane.showMessageDialog(ui, "Cancel the reservation");
			}
			/*
			 * å�� �����ϴ� �κ� ���� å�� �˻��� �� ������ å�� �����ϱ⸦ ������ å�� ������ ������� ���̵� �޾Ƽ� �޽�����
			 * ��Ű¡ �� �� ������ �������� �����Ѵ�. �̶� ������ ����ϰ� ������ ��� or �ƴϿ� �� ������ ��ҵǾ��ٴ� ������
			 * Ȯ�� â���� �߸� ������ ���� �Ѵٸ� ���� ������ �α׾ƿ� �� �� �ٽ� �α��� �ؾ� ���� �����Ѵ�.
			 */
		}
		else if(obj == ui.getbtnRegist()) {
			//ȸ������
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
			System.out.println("ȸ�� �����մϴ�.");
			/*
			 * ȸ�� ������ �ϱ� ���� ��ư�� �������� ���� �̋����� view ��Ʈ�� ������ �� ����� ���� ���̾�α׿� ����
			 * ����ϰ� ���� ���̵�� ��й�ȣ �׸��� ����� �̸��� ��� �����ָ� �̸� ��Ű¡�ؼ� ������ �����ش�.
			 */
		}
		else if(obj == ui.getbtnExit()) {
			//����
			System.out.println("���α׷��� �����մϴ�.");
			userData = new UserData();
			userData.setUserID(ui.loginID());
			userData.setFlag(false);
			message = new MessageData();
			message.setUserData(userData);
			message.setType(ClientConstant.LOGOUT);
			if(socket.sendToLogoutMessage(message)) { // true --> network communication success is need
				System.out.println("�α׾ƿ� �մϴ�.");
				ui.showLoginPanel();
			}
			socket.close();
			
			System.exit(0);
			/*
			 * ���α׷� �����ư�� �������� ����
			 * �α����� �� ���¿����� ���̴� ��ư�̹Ƿ� �����ư�� �����ٸ� �α׾ƿ��� ���� ����� �ϹǷ� �α׾ƿ� �ϰڴٴ�
			 * �޽����� ������ �����ϴ� ��ƾ�� �α׾ƿ��ϴ� ��ư�� �����ο��� �����ͼ� ���� ����� ���� ������ �ݾ� ������ �״°���
			 * �̸� �����ϰ� Ŭ���̾�Ʈ �κ� ���α׷��� �����Ѵ�.
			 */
		}
	}

}
