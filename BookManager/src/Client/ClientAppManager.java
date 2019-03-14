package Client;

import ClientUI.ClientFrame;
import ClientUI.ClientUI;

public class ClientAppManager {
	/*
	 * 싱글톤 패턴 적용을 위해 만들어둔 애플리케이션 매니저
	 */
	private ClientUI clientUI;
	private ClientFrame clientFrame;
	//클라이언트 프로그램 부분에서 view 파트로 구분할 수 있는 frame 과 panel 부분의 클래스를 각각 하나씩만 만들기 위해 미리 선언돈
	//부분이다.
	private static ClientAppManager clientAppManager;
	//앱 매니저 자신은 한번 생성되면 또 생성되면 안되므로 이를 static으로 선언하여 이후 앱매니저를 통해 패널을 부를때는 항상
	//이 static 변수를 호출하여 사용하도록 한다.
	public ClientAppManager() {
		
	}
	
	public static ClientAppManager getClinetAppManager() {
		if(clientAppManager == null) {
			clientAppManager = new ClientAppManager();
		}
		return clientAppManager;
		//만약 앱매니저를 가져오라고 했는데 아직 한번도 생성하지 않았다면 생성하고 아니라면 그냥 이 static 변수를 가져온다.
	}
	
	public ClientUI getClientUI() {
		return this.clientUI;
	}
	//패널을 가져오는 메소드
	
	public void setClientUI(ClientUI ui) {
		this.clientUI = ui;
	}
	//패널을 설정하는 메소드
	
	public ClientFrame getClientFrame() {
		return this.clientFrame;
	}
	//프레임을 가져오는 메소드
	
	public void setClientFrame(ClientFrame frame) {
		this.clientFrame = frame;
	}
	//프레임을 설정하는 메소드
	
}
