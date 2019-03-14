package Server;

import ServerUI.ServerFrame;

public class ServerMain {
	/*
	 * 서버를 시작하는 메인 클래스
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerFrame frame = new ServerFrame();
		RequestController RC = new RequestController();
		RC.Start();
		//--> 서버의 UI와 서버가 클라이언트에게 정보를 받아 처리하는 부분은 서로 다르기 때문에 이를 맞춰 각각을 실행한다.
	}

}
