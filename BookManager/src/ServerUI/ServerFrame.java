package ServerUI;

import javax.swing.JFrame;

import Server.ServerAppManger;
import Server.SeverActionListener;

public class ServerFrame extends JFrame {
	/*
	 * 서버 UI부분의 모든 패널을 담을 프레임 여기에 패널의 액션 리스너도 같이 생성해 애드한다.
	 */
	private ServerPrimaryPanel primaryPanel;
	private SeverActionListener act;

	public ServerFrame() {
		super(":: server ::");
		ServerAppManger.getS_instance().setS_ServerFrame(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		primaryPanel = new ServerPrimaryPanel();
		act = new SeverActionListener();
		primaryPanel.AddEvent(act);
		getContentPane().add(primaryPanel);
		setVisible(true);
		pack();
	}
}
