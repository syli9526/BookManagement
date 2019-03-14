package ClientUI;

import javax.swing.JFrame;

import Client.ClientActionListener;
import Client.ClientAppManager;

public class ClientFrame extends JFrame{
	/*
	 * Ŭ���̾�Ʈ ���α׷��� view ��Ʈ�� �ϳ��� �κ�
	 * �������� ����ϴ� �κ�
	 */
	private ClientUI c;
	private ClientActionListener CAL;
	//�����ӿ��� �гΰ� �гο� ���� �׼� �����ʸ� ����
	
	public ClientFrame(){
		this.setTitle("Client");
		ClientAppManager.getClinetAppManager().setClientFrame(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c = new ClientUI();
		c.createUI();
		CAL = new ClientActionListener();
		c.addActionListener(CAL);
		this.getContentPane().add(c);
		
		this.pack();
		this.setVisible(true);
		//�����ӿ� ���õ� �������� ���̽����� �����ϰ� �׼Ǹ����ʿ� �г��� ���� �гξ��� ��ư�鿡 �� �׼� ������ �ֵ带 
		//���� ����� �� �׼� �����ʸ� �Ķ���ͷ� ������ �����Ѵ�.
	}
}
