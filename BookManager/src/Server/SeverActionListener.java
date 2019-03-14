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
	 * 서버의 UI에서 버튼을 눌렀을때 이에 따른 행동을 정의 하기 위한 클래스이다
	 * 즉 서버 UI 부분의 컨트롤러 부분이다.
	 */
    private ServerPrimaryPanel primary = ServerAppManger.getS_instance().getsPrimPanel();
    private ServerBookPanel bookPanel = ServerAppManger.getS_instance().getsBookPanel();
    private ServerUserPanel userPanel = ServerAppManger.getS_instance().getsUserPanel();
    private ServerReservationPanel reservationPanel = ServerAppManger.getS_instance().getsReservationPanel();
    private ServerRentPanel rentPanel = ServerAppManger.getS_instance().getServerRentPanel();
    private BookInfoDAO daoB = new BookInfoDAO();
    private UserInfoDAO daoU = new UserInfoDAO();
    //--> 서버는 직접 DB에 접근해 정보를 수정하므로 DAO를 선언하고 생성한다.

    public SeverActionListener() {

        bookPanel.refreshData(getBookInfo(), false);

    } //--> 클래스 생성자
    
    /*
     * DB에 접속하여 이벤트 핸들러가 요구하는 정보를 받아오는 메소드들
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
    	//--> 실질적인 이벤트 핸들링 부분
        if (event.getSource() == primary.getBtnBookManager()) {
        	//--> 책 관리 패널을 켜고자 할때 버튼을 눌렀다면
            bookPanel.refreshData(getBookInfo(), false);
            primary.showBook();
            //--> 테이블에 필요한 정보를 받고 책 관리자 패널을 보여준다.
        } else if (event.getSource() == primary.getBtnUserManager()) {
        	//--> 사용자 관리 패널을 켜고자 할때 버튼을 눌렀다면
            userPanel.refreshData(getUserInfo(), false);
            primary.showUser();
            //--> 테이블에 필요한 정보를 받고 사용자 관리자 패널을 보여준다.
        } else if (event.getSource() == primary.getBtnReservationManager()) {
        	//--> 예약 정보 관리를 위한 패널을 켜고자 버튼을 누를떄
            reservationPanel.refreshData(getReservationInfo(), false);
            primary.showReservation();
        } else if (event.getSource() == primary.getBtnRentalManager()) {
        	//--> 대여 정보 관리를 위한 패널을 켜고자 버튼을 누를때
            rentPanel.refreshData(getRentInfo(), false);
            primary.showRental();
        }
        // primary Panel의 버튼에 대한 액션 처리
        else if (event.getSource() == bookPanel.getBtnEnter()) {
        	//--> 텍스트 필드에 정보를 넣고자 엔터를 누를때
            bookPanel.refreshData(getBookInfo(), true);
        } else if (event.getSource() == bookPanel.getBtnSearch()) {
        	//--> 정보에 맞춰 검색을 하고자 할때
            String txt = bookPanel.getTxtSearchInfo().getText();
            String sql = " where " + ServerConstant.BOOK[bookPanel.getCombox().getSelectedIndex()] + " = '" + txt + "'";
            bookPanel.refreshData(daoB.getBookData(sql), false);
        } else if (event.getSource() == bookPanel.getBtnInsert()) {
        	//-->책을 등록하기 위한 버튼을 누를때

            BookData data = bookPanel.getBookInfo();

            if (!data.getBookID().equals("")) {

                if (!daoB.insertBook(data))
                    JOptionPane.showMessageDialog(bookPanel, "Error", "error!", JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(bookPanel, "Error", "error!", JOptionPane.ERROR_MESSAGE);
            bookPanel.refreshData(getBookInfo(), false);


        } else if (event.getSource() == bookPanel.getBtnDelete()) {
        	//--> 책을 삭제하기 위한 버튼을 누를때

            BookData data = bookPanel.getBookInfo();
            if (!daoB.delBook(data.getBookID()))
                JOptionPane.showMessageDialog(bookPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);

            bookPanel.refreshData(getBookInfo(), false);

        }
        //--> 책 정보패널에 대한 버튼의 이벤트 핸들링
        else if (event.getSource() == userPanel.getBtnEnter()) {
        	//--> 텍스트 필드에 정보를 담고자 엔터를 누를때
            userPanel.refreshData(getUserInfo(), true);
        } else if (event.getSource() == userPanel.getBtnSearch()) {
        	//--> 조건에 맞춰 검색하여 테이블에 띄우고자 버튼을 누를때
            String txt = userPanel.getTxtSearchInfo().getText();
            String sql = " where " + ServerConstant.USER[userPanel.getCombox().getSelectedIndex()] + " = '" + txt + "'";
            daoU.getUser(sql);
            userPanel.refreshData(daoU.getUser(sql), false);
        } else if (event.getSource() == userPanel.getBtnInsert()) {
        	//사용자를 등록하고자 할때
            UserData data = userPanel.getUserInfo();
            if (!data.getUserID().equals("") && !data.getUserPassword().equals("")) {
                if (!daoU.insertUser(data))
                    JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);

            }
            userPanel.refreshData(getUserInfo(), false);
        } else if (event.getSource() == userPanel.getBtnUpdate()) {
        	//사용자 정보를 업데이트 하고자 할때
            UserData data = userPanel.getUserInfo();
            if (daoU.upDateUser(data))
                userPanel.refreshData(getUserInfo(), false);
            else {
                JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
                userPanel.refreshData(getUserInfo(), false);
            }
        } else if (event.getSource() == userPanel.getBtnDelete()) {
        	//사용자 정보를 삭제하고자 할때
            UserData data = userPanel.getUserInfo();
            if (daoU.delUser(data.getUserID()))
                userPanel.refreshData(getUserInfo(), false);
            else {
                JOptionPane.showMessageDialog(userPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
                userPanel.refreshData(getUserInfo(), false);
            }
        }
        //--> 사용자 패널에 들어있는 버튼에 대한 이벤트 핸들링
        else if (event.getSource() == rentPanel.getBtnEnter()) {
        	//대여 정보를 텍스트 필드에 넣고자 할때
            rentPanel.refreshData(getRentInfo(), true);
        } else if (event.getSource() == rentPanel.getBtnSearch()) {
        	//--> 대여정보를 검색할때
            String txt = rentPanel.getTxtSearchInfo().getText();
            String sql = " where " + ServerConstant.BOOK[rentPanel.getCombox().getSelectedIndex()] + " = '" + txt + "'";
            rentPanel.refreshData(daoB.getRentData(sql), false);
        } else if (event.getSource() == rentPanel.getBtnRental()) {
        	//--> 책을 대여할때
            BookData data = rentPanel.getRentInfo();
            if (!daoB.rentBook(data))
                JOptionPane.showMessageDialog(rentPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            rentPanel.refreshData(getRentInfo(), false);
        } else if (event.getSource() == rentPanel.getBtnReturn()) {
        	//--> 책을 반납할때
            BookData data = rentPanel.getRentInfo();
            if (!daoB.returnBook(data))
                JOptionPane.showMessageDialog(rentPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            rentPanel.refreshData(getRentInfo(), false);
        }
        //--> 대여 정보를 관리하는 패널에 버튼에 대한 이벤트 핸들링
        else if (event.getSource() == reservationPanel.getBtnEnter()) {
        	//--> 예약 정보를 텍스트 필드에 넣고자 할때
            reservationPanel.refreshData(getReservationInfo(), true);
        } else if (event.getSource() == reservationPanel.getBtnSearch()) {
        	//--> 예약 정보를 검색하고자 할때

            String sql;
            if(reservationPanel.getCombox().getSelectedItem().toString() == "reservable") {
            	sql = " where reservation_info.bookID is null and rent_info.bookID is not null";	
            }
            else {
            	sql = " where reservation_info.bookID is not null and rent_info.bookID is not null";	
            }
            /*
             * 예약 정보 검색의 경우 sql문이 여타 검색과는 다르다 외래키를 통해 조인하여 검색하는데
             * 이때 예약 가능은 대출은 되어있지만 예약은 안된것이고 
             * 예약 불가는 대출과 예약이 모두 되어 있는 정보엣 유효하다.
             */
            reservationPanel.refreshData(daoB.getReservationData(sql), false);
        } else if (event.getSource() == reservationPanel.getBtnReservation()) {
        	//--> 예약을 하고자 할때 

            BookData data = reservationPanel.getReservationInfo();

            if (!daoB.reserveBook(data))
                JOptionPane.showMessageDialog(reservationPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            reservationPanel.refreshData(getReservationInfo(), false);

        } else if (event.getSource() == reservationPanel.getBtnCanel()) {
        	//--> 예약을 취소하고자 할때

            BookData data = reservationPanel.getReservationInfo();

            if (!daoB.cancelReserveBook(data))
                JOptionPane.showMessageDialog(reservationPanel, "error", "error!", JOptionPane.ERROR_MESSAGE);
            reservationPanel.refreshData(getReservationInfo(), false);
        }
        //--> 예약 정보를 관리하는 패널에 붙어있는 버튼의 이벤트 핸들링을 관리하는 부분

    }


}


