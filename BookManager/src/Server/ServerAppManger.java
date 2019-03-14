package Server;

import ServerUI.*;

public class ServerAppManger {
	/*
	 * 클라이언트 앱 매니저와 같은 패널의 중복 생성을 막기위한 싱글톤 패턴 적용의 클래스
	 * 모든 논리는 클라이언트 앱 매니저와 같다.
	 */
    private static ServerAppManger s_instance;
    private ServerFrame sFrame;
    private ServerBookPanel sBookPanel;
    private ServerPrimaryPanel sPrimPanel;
    private ServerReservationPanel sReservationPanel;
    private ServerRentPanel serverRentPanel;
    private ServerUserPanel sUserPanel;

    private ServerAppManger() {

    }

    public static ServerAppManger getS_instance() {
        if (s_instance == null)
            s_instance = new ServerAppManger();
        return s_instance;
    }

    public ServerReservationPanel getsReservationPanel() {
        return sReservationPanel;
    }

    public void setsReservationPanel(ServerReservationPanel sReservationPanel) {
        this.sReservationPanel = sReservationPanel;
    }

    public ServerRentPanel getServerRentPanel() {
        return serverRentPanel;
    }

    public void setServerRentPanel(ServerRentPanel serverRentPanel) {
        this.serverRentPanel = serverRentPanel;
    }

    public void setS_ServerFrame(ServerFrame sFrame) {
        this.sFrame = sFrame;
    }

    public ServerFrame getsServerFrame() {
        return sFrame;
    }

    public ServerBookPanel getsBookPanel() {
        return sBookPanel;
    }

    public void setsBookPanel(ServerBookPanel sBookPanel) {
        this.sBookPanel = sBookPanel;
    }

    public ServerPrimaryPanel getsPrimPanel() {
        return sPrimPanel;
    }

    public void setsPrimPanel(ServerPrimaryPanel sPrimPanel) {
        this.sPrimPanel = sPrimPanel;
    }

    public ServerUserPanel getsUserPanel() {
        return sUserPanel;
    }

    public void setsUserPanel(ServerUserPanel sUserPanel) {
        this.sUserPanel = sUserPanel;
    }

}
