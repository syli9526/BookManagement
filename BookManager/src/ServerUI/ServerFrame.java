package ServerUI;

import javax.swing.JFrame;

import Server.ServerAppManger;
import Server.SeverActionListener;

public class ServerFrame extends JFrame {
	/*
	 * ���� UI�κ��� ��� �г��� ���� ������ ���⿡ �г��� �׼� �����ʵ� ���� ������ �ֵ��Ѵ�.
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
