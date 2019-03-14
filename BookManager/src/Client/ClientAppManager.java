package Client;

import ClientUI.ClientFrame;
import ClientUI.ClientUI;

public class ClientAppManager {
	/*
	 * �̱��� ���� ������ ���� ������ ���ø����̼� �Ŵ���
	 */
	private ClientUI clientUI;
	private ClientFrame clientFrame;
	//Ŭ���̾�Ʈ ���α׷� �κп��� view ��Ʈ�� ������ �� �ִ� frame �� panel �κ��� Ŭ������ ���� �ϳ����� ����� ���� �̸� ����
	//�κ��̴�.
	private static ClientAppManager clientAppManager;
	//�� �Ŵ��� �ڽ��� �ѹ� �����Ǹ� �� �����Ǹ� �ȵǹǷ� �̸� static���� �����Ͽ� ���� �۸Ŵ����� ���� �г��� �θ����� �׻�
	//�� static ������ ȣ���Ͽ� ����ϵ��� �Ѵ�.
	public ClientAppManager() {
		
	}
	
	public static ClientAppManager getClinetAppManager() {
		if(clientAppManager == null) {
			clientAppManager = new ClientAppManager();
		}
		return clientAppManager;
		//���� �۸Ŵ����� ��������� �ߴµ� ���� �ѹ��� �������� �ʾҴٸ� �����ϰ� �ƴ϶�� �׳� �� static ������ �����´�.
	}
	
	public ClientUI getClientUI() {
		return this.clientUI;
	}
	//�г��� �������� �޼ҵ�
	
	public void setClientUI(ClientUI ui) {
		this.clientUI = ui;
	}
	//�г��� �����ϴ� �޼ҵ�
	
	public ClientFrame getClientFrame() {
		return this.clientFrame;
	}
	//�������� �������� �޼ҵ�
	
	public void setClientFrame(ClientFrame frame) {
		this.clientFrame = frame;
	}
	//�������� �����ϴ� �޼ҵ�
	
}
