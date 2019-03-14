package ClientUI;

import javax.swing.JFrame;

import Client.ClientActionListener;
import Client.ClientAppManager;

public class ClientFrame extends JFrame{
	/*
	 * 클라이언트 프로그램의 view 파트중 하나인 부분
	 * 프레임을 담당하는 부분
	 */
	private ClientUI c;
	private ClientActionListener CAL;
	//프레임에서 패널과 패널에 붙일 액션 리스너를 선언
	
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
		//프레임에 관련된 기초적인 베이스들을 구현하고 액션리스너와 패널을 만들어서 패널안의 버튼들에 상세 액션 리스너 애드를 
		//따로 만들어 둔 액션 리스너를 파라미터로 보내서 구현한다.
	}
}
