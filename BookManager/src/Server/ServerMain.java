package Server;

import ServerUI.ServerFrame;

public class ServerMain {
	/*
	 * ������ �����ϴ� ���� Ŭ����
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerFrame frame = new ServerFrame();
		RequestController RC = new RequestController();
		RC.Start();
		//--> ������ UI�� ������ Ŭ���̾�Ʈ���� ������ �޾� ó���ϴ� �κ��� ���� �ٸ��� ������ �̸� ���� ������ �����Ѵ�.
	}

}
