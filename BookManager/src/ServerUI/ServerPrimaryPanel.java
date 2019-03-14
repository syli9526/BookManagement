package ServerUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Server.ServerAppManger;
import Server.ServerConstant;

public class ServerPrimaryPanel extends JPanel {
	/*
	 * 서버 부분의 패널을 보여주는 부모 패널
	 * 모든 패널들을 가지고 있는 부모 패널이며 여기에서 각종 정보를 받아서 패널에 뿌려주던가 아니면
	 * 카드 레이아웃을 통해 어떤 패널을 위로 올릴건지 선택하고 반영하는 등의 액션을 취한다.
	 * 존재하는 모든 메소드들은 앞의 bookPanel에서 설명한것과 같은 역할을 한다.
	 */

    private JButton btnBookManager;
    private JButton btnUserManager;
    private JButton btnReservationManager;
    private JButton btnRentalManager;
    private ServerBookPanel bookPanel;
    private ServerUserPanel userPanel;
    private ServerRentPanel rentPanel;
    private ServerReservationPanel reservationPanel;

    private JPanel mid;
    private CardLayout cardLayout;

    public ServerPrimaryPanel() {
    	//생성자
        setPreferredSize(new Dimension(800, 600));

        ServerAppManger.getS_instance().setsPrimPanel(this);
        setLayout(null);
        setUI();
    }

    private void CreateComponent() {
    	//컴포넌트 생성
        btnBookManager = new JButton(ServerConstant.M_BOOK);
        btnUserManager = new JButton(ServerConstant.M_USER);
        btnReservationManager = new JButton(ServerConstant.M_RESERVATION);
        btnRentalManager = new JButton(ServerConstant.M_RENT);

        bookPanel = new ServerBookPanel();
        userPanel = new ServerUserPanel();
        rentPanel = new ServerRentPanel();
        reservationPanel = new ServerReservationPanel();

        mid = new JPanel();
        cardLayout = new CardLayout();
    }

    private void AddComponent() {
    	//컴포넌트 추가
        add(btnBookManager);
        add(btnUserManager);
        add(btnReservationManager);
        add(btnRentalManager);
        add(mid);

    }

    private void setUI() {
    	//UI배치 설정
        CreateComponent();

        btnBookManager.setBounds(425, 20, 150, 60);
        btnUserManager.setBounds(625, 20, 150, 60);
        btnReservationManager.setBounds(225,20,150,60);
        btnRentalManager.setBounds(25,20,150,60);

        mid.setBounds(20, 100, 760, 480);
        mid.setLayout(cardLayout);
        mid.add(bookPanel, "book");
        mid.add(userPanel, "user");
        mid.add(reservationPanel, "reservation");
        mid.add(rentPanel, "rental");
        cardLayout.show(mid, "book");
        AddComponent();
    }

    public void AddEvent(ActionListener act) {
    	//이벤트 리스너를 애드
        btnBookManager.addActionListener(act);
        btnUserManager.addActionListener(act);
        btnReservationManager.addActionListener(act);
        btnRentalManager.addActionListener(act);
        bookPanel.AddEvent(act);
        userPanel.AddEvent(act);
        rentPanel.AddEvent(act);
        reservationPanel.AddEvent(act);
    }

    /*
     * 카드 레이아웃을 기반으로 어떤 패널이 위로 올라오게 만들것인지를 선택하는 show 메소드들
     */
    public void showBook() {
        cardLayout.show(mid, "book");
    }

    public void showUser() {
        cardLayout.show(mid, "user");
    }

    public void showReservation() {
        cardLayout.show(mid, "reservation");
    }

    public void showRental(){
        cardLayout.show(mid, "rental");

    }
    
    /*
     * 각종 (get/set) ter들
     */
    public JButton getBtnReservationManager() {
        return btnReservationManager;
    }

    public void setBtnReservationManager(JButton btnReservationManager) {
        this.btnReservationManager = btnReservationManager;
    }


    public JButton getBtnRentalManager() {
        return btnRentalManager;
    }

    public void setBtnRentalManager(JButton btnRentalManager) {
        this.btnRentalManager = btnRentalManager;
    }

    public JButton getBtnBookManager() {
        return btnBookManager;
    }

    public void setBtnBookManager(JButton btnBookManager) {
        this.btnBookManager = btnBookManager;
    }

    public JButton getBtnUserManager() {
        return btnUserManager;
    }

    public void setBtnUserManager(JButton btnUserManager) {
        this.btnUserManager = btnUserManager;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

}
