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
	 * ���� �κ��� �г��� �����ִ� �θ� �г�
	 * ��� �гε��� ������ �ִ� �θ� �г��̸� ���⿡�� ���� ������ �޾Ƽ� �гο� �ѷ��ִ��� �ƴϸ�
	 * ī�� ���̾ƿ��� ���� � �г��� ���� �ø����� �����ϰ� �ݿ��ϴ� ���� �׼��� ���Ѵ�.
	 * �����ϴ� ��� �޼ҵ���� ���� bookPanel���� �����ѰͰ� ���� ������ �Ѵ�.
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
    	//������
        setPreferredSize(new Dimension(800, 600));

        ServerAppManger.getS_instance().setsPrimPanel(this);
        setLayout(null);
        setUI();
    }

    private void CreateComponent() {
    	//������Ʈ ����
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
    	//������Ʈ �߰�
        add(btnBookManager);
        add(btnUserManager);
        add(btnReservationManager);
        add(btnRentalManager);
        add(mid);

    }

    private void setUI() {
    	//UI��ġ ����
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
    	//�̺�Ʈ �����ʸ� �ֵ�
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
     * ī�� ���̾ƿ��� ������� � �г��� ���� �ö���� ����������� �����ϴ� show �޼ҵ��
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
     * ���� (get/set) ter��
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
