package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import DAO.BookInfoDAO;
import DAO.UserInfoDAO;
import DataStructure.BookData;
import DataStructure.UserData;
import ServerUI.*;

import javax.swing.*;

public class SeverActionListener implements ActionListener {
	/*
	 * ������ UI���� ��ư�� �������� �̿� ���� �ൿ�� ���� �ϱ� ���� Ŭ�����̴�
	 * �� ���� UI �κ��� ��Ʈ�ѷ� �κ��̴�.
	 */
    private ServerPrimaryPanel primary = ServerAppManger.getS_instance().getsPrimPanel();
    private ServerBookPanel bookPanel = ServerAppManger.getS_instance().getsBookPanel();
    private ServerUserPanel userPanel = ServerAppManger.getS_instance().getsUserPanel();
    private ServerReservationPanel reservationPanel = ServerAppManger.getS_instance().getsReservationPanel();
    private ServerRentPanel rentPanel = ServerAppManger.getS_instance().getServerRentPanel();
    private BookInfoDAO daoB = new BookInfoDAO();
    private UserInfoDAO daoU = new UserInfoDAO();
    //--> ������ ���� DB�� ������ ������ �����ϹǷ� DAO�� �����ϰ� �����Ѵ�.

    public SeverActionListener() {

        bookPanel.refreshData(getBookInfo(), false);

    } //--> Ŭ���� ������
    
    /*
     * DB�� �����Ͽ� �̺�Ʈ �ڵ鷯�� �䱸�ϴ� ������ �޾ƿ��� �޼ҵ��
     * 
     */

    public ArrayList<BookData> getBookInfo() {

        return daoB.getBookData("");

    }

    public ArrayList<UserData> getUserInfo() {

        return daoU.getUser("");
    }

    public ArrayList<BookData> getRentInfo() {
        return daoB.getRentData("");
    }


    public ArrayList<BookData> getReservationInfo() {
        return daoB.getReservationData(null);
    }
    

    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO Auto-generated method stub
    	//--> �������� �̺�Ʈ �ڵ鸵 �κ�
        if (event.getSource() == primary.getBtnBookManager()) {
        	//--> å ���� �г��� �Ѱ��� �Ҷ� ��ư�� �����ٸ�
            bookPanel.refreshData(getBookInfo(), false);
            primary.showBook();
            //--> ���̺� �ʿ��� ������ �ް� å ������ �г��� �����ش�.
        } else if (event.getSource() == primary.getBtnUserManager()) {
        	//--> ����� ���� �г��� �Ѱ��� �Ҷ� ��ư�� �����ٸ�
            userPanel.refreshData(getUserInfo(), false);
            primary.showUser();
            //--> ���̺� �ʿ��� ������ �ް� ����� ������ �г��� �����ش�.
        } else if (event.getSource() == primary.getBtnReservationManager()) {
        	//--> ���� ���� ������ ���� �г��� �Ѱ��� ��ư�� ������
            reservationPanel.refreshData(getReservationInfo(), false);
            primary.showReservation();
        } else if (event.getSource() == primary.getBtnRentalManager()) {
        	//--> �뿩 ���� ������ ���� �г��� �Ѱ��� ��ư�� ������
            rentPanel.refreshData(getRentInfo(), false);
            primary.showRental();
        }
        // primary Panel�� ��ư�� ���� �׼� ó��
        else if (event.getSource() == bookPanel.getBtnEnter()) {
        	//--> �ؽ�Ʈ �ʵ忡 ������ �ְ��� ���͸� ������
            bookPanel.refreshData(getBookInfo(), true);
        } else if (event.getSource() == bookPanel.getBtnSearch()) {
        	//--> ������ ���� �˻��� �ϰ��� �Ҷ�
            String txt = bookPanel.getTxtSearchInfo().getText();
            String sql = " where " + ServerConstant.BOOK[bookPanel.getCombox().getSelectedIndex()] + " = '" + txt + "'";
            bookPanel.refreshData(daoB.getBookData(sql), false);
        } else if (event.getSource() == bookPanel.getBtnInsert()) {
        	//-->å�� ����ϱ� ���� ��ư�� ������

            BookData data = bookPanel.getBookInfo();

            if (!data.getBookID().equals("")) {

                if (!daoB.insertBook(data))
                    JOptionPane.showMessageDialog(bookPanel, "Error", "error!", JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(bookPanel, "Error", "error!", JOptionPane.ERROR_MESSAGE);
            bookPanel.refreshData(getBookInfo(), false);


        } else if (event.getSource() == bookPanel.getBtnDelete()) {
        	//--> å�� �����ϱ� ���� ��ư�� ������

            BookData data = bookPanel.getBookInfo();
            if (!daoB.delBook(data.getBookID()))
                JOptionPane.showMessageDialog(bookPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);

            bookPanel.refreshData(getBookInfo(), false);

        }
        //--> å �����гο� ���� ��ư�� �̺�Ʈ �ڵ鸵
        else if (event.getSource() == userPanel.getBtnEnter()) {
        	//--> �ؽ�Ʈ �ʵ忡 ������ ����� ���͸� ������
            userPanel.refreshData(getUserInfo(), true);
        } else if (event.getSource() == userPanel.getBtnSearch()) {
        	//--> ���ǿ� ���� �˻��Ͽ� ���̺� ������ ��ư�� ������
            String txt = userPanel.getTxtSearchInfo().getText();
            String sql = " where " + ServerConstant.USER[userPanel.getCombox().getSelectedIndex()] + " = '" + txt + "'";
            daoU.getUser(sql);
            userPanel.refreshData(daoU.getUser(sql), false);
        } else if (event.getSource() == userPanel.getBtnInsert()) {
        	//����ڸ� ����ϰ��� �Ҷ�
            UserData data = userPanel.getUserInfo();
            if (!data.getUserID().equals("") && !data.getUserPassword().equals("")) {
                if (!daoU.insertUser(data))
                    JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);

            }
            userPanel.refreshData(getUserInfo(), false);
        } else if (event.getSource() == userPanel.getBtnUpdate()) {
        	//����� ������ ������Ʈ �ϰ��� �Ҷ�
            UserData data = userPanel.getUserInfo();
            if (daoU.upDateUser(data))
                userPanel.refreshData(getUserInfo(), false);
            else {
                JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
                userPanel.refreshData(getUserInfo(), false);
            }
        } else if (event.getSource() == userPanel.getBtnDelete()) {
        	//����� ������ �����ϰ��� �Ҷ�
            UserData data = userPanel.getUserInfo();
            if (daoU.delUser(data.getUserID()))
                userPanel.refreshData(getUserInfo(), false);
            else {
                JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
                userPanel.refreshData(getUserInfo(), false);
            }
        }
        //--> ����� �гο� ����ִ� ��ư�� ���� �̺�Ʈ �ڵ鸵
        else if (event.getSource() == rentPanel.getBtnEnter()) {
        	//�뿩 ������ �ؽ�Ʈ �ʵ忡 �ְ��� �Ҷ�
            rentPanel.refreshData(getRentInfo(), true);
        } else if (event.getSource() == rentPanel.getBtnSearch()) {
        	//--> �뿩������ �˻��Ҷ�
            String txt = rentPanel.getTxtSearchInfo().getText();
            String sql = " where " + ServerConstant.BOOK[rentPanel.getCombox().getSelectedIndex()] + " = '" + txt + "'";
            rentPanel.refreshData(daoB.getRentData(sql), false);
        } else if (event.getSource() == rentPanel.getBtnRental()) {
        	//--> å�� �뿩�Ҷ�
            BookData data = rentPanel.getRentInfo();
            if (!daoB.rentBook(data))
                JOptionPane.showMessageDialog(rentPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            rentPanel.refreshData(getRentInfo(), false);
        } else if (event.getSource() == rentPanel.getBtnReturn()) {
        	//--> å�� �ݳ��Ҷ�
            BookData data = rentPanel.getRentInfo();
            if (!daoB.returnBook(data))
                JOptionPane.showMessageDialog(rentPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            rentPanel.refreshData(getRentInfo(), false);
        }
        //--> �뿩 ������ �����ϴ� �гο� ��ư�� ���� �̺�Ʈ �ڵ鸵
        else if (event.getSource() == reservationPanel.getBtnEnter()) {
        	//--> ���� ������ �ؽ�Ʈ �ʵ忡 �ְ��� �Ҷ�
            reservationPanel.refreshData(getReservationInfo(), true);
        } else if (event.getSource() == reservationPanel.getBtnSearch()) {
        	//--> ���� ������ �˻��ϰ��� �Ҷ�

            String sql;
            if(reservationPanel.getCombox().getSelectedItem().toString() == "reservable") {
            	sql = " where reservation_info.bookID is null and rent_info.bookID is not null";	
            }
            else {
            	sql = " where reservation_info.bookID is not null and rent_info.bookID is not null";	
            }
            /*
             * ���� ���� �˻��� ��� sql���� ��Ÿ �˻����� �ٸ��� �ܷ�Ű�� ���� �����Ͽ� �˻��ϴµ�
             * �̶� ���� ������ ������ �Ǿ������� ������ �ȵȰ��̰� 
             * ���� �Ұ��� ����� ������ ��� �Ǿ� �ִ� ������ ��ȿ�ϴ�.
             */
            reservationPanel.refreshData(daoB.getReservationData(sql), false);
        } else if (event.getSource() == reservationPanel.getBtnReservation()) {
        	//--> ������ �ϰ��� �Ҷ� 

            BookData data = reservationPanel.getReservationInfo();

            if (!daoB.reserveBook(data))
                JOptionPane.showMessageDialog(reservationPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            reservationPanel.refreshData(getReservationInfo(), false);

        } else if (event.getSource() == reservationPanel.getBtnCanel()) {
        	//--> ������ ����ϰ��� �Ҷ�

            BookData data = reservationPanel.getReservationInfo();

            if (!daoB.cancelReserveBook(data))
                JOptionPane.showMessageDialog(reservationPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            reservationPanel.refreshData(getReservationInfo(), false);
        }
        //--> ���� ������ �����ϴ� �гο� �پ��ִ� ��ư�� �̺�Ʈ �ڵ鸵�� �����ϴ� �κ�

    }


}


