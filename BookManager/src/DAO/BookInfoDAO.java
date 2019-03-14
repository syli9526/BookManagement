package DAO;

import DataStructure.BookData;
import DataStructure.UserData;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookInfoDAO {
    private LinkDB db;

    /*
     * 책 정보를 DB에 연결해서 받아오고 수정하는 일을 모두 담당하는 DAO 클래스
     */
    
    public BookInfoDAO() {

        db = new LinkDB();
        //생성자에서 DB에 DAO를 연결하는 기초 작업을 시행한다.
    }//BookInfoDAO()

    //rent
    public ArrayList<BookData> getRentData(String condi) {
    	//대여한 책들의 장보만을 담아와서 ArrayList로 리턴하는 메소드
        db.connectDB();
        //우선 DB에 연결을 하고
        String sql = "select book.bookID, bname, author, publish ,rentID, startdate, enddate from book left join rent_info on book.bookID = rent_info.bookID";
        //sql문을 초반 설정하는데 대출 정보와 예약 정보는 테이블을 따로 구현해서 이 정보를 보이게 하기 위해서는
        //외래키를 사용해 정보를 받아와야 한다. 이를 위해 초반 DB의 테이블을 구성할때 외래키를 이용해였다.
        sql += condi;
        //이 sql 뒤에 추가로 어떤 정보를 통해 검색할건지 상세 문자열이 추가로 파라미터로 들어와 구현된다.
        ArrayList<BookData> datas = new ArrayList<BookData>();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.rs = db.pstmt.executeQuery();

            //    public BookData(String bookID, String bookName, String author, String publish, String rentUserID, Date startDate, Date endDate)
            while (db.rs.next()) {

                BookData temp = new BookData(
                        db.rs.getString("bookID"),
                        db.rs.getString("bname"),
                        db.rs.getString("author"),
                        db.rs.getString("publish"),
                        db.rs.getString("rentID"),
                        db.rs.getString("startDate"),
                        db.rs.getString("enddate")
                );

                datas.add(temp);

            }// while
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
         * DB에 연결하여 sql문을 돌리고 얻은 결과를 쭉 진행
         */
        
        db.connectDB();
        //DB연결을 끊고
        if (!datas.isEmpty())
            return datas;
        else
            return null;
        //ArrayList가 null이 아니라면 반환하고 null이면 null로 반환한다.
    }//getRentData()

    //reservation
    public ArrayList<BookData> getReservationData(String condi) {
    	//예약 정보를 DB를 통해 받아오는 메소드
        db.connectDB();
        String sql = "select book.bookID, bname, author, publish ,rentID, startdate, enddate, reservationID from book left join rent_info on book.bookID = rent_info.bookID left join reservation_info on rent_info.bookID = reservation_info.bookID";

        if(condi == null) {
        	sql = "select book.bookID, bname, author, publish ,rentID, startdate, enddate, reservationID from book left join rent_info on book.bookID = rent_info.bookID left join reservation_info on rent_info.bookID = reservation_info.bookID where reservation_info.bookID is not null and rent_info.bookID is not null";
        	//어떠한 where 정보 파라미터도 없다면 우선 서버 UI단에서 콤보 박스가 예약된 책을 먼저 띄우도록 설정하기 위해 이 sql
        	//명령문을 설정하고
        }
        else {
        	sql += condi;
        	//파라미터가 따로 들어오면 그걸 더해서 검색조건을 갖춘다.
        }
        ArrayList<BookData> datas = new ArrayList<BookData>();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.rs = db.pstmt.executeQuery();

            //    public BookData(String bookID, String bookName, String author, String publish, String rentUserID, Date startDate, Date endDate)
            while (db.rs.next()) {

                BookData temp = new BookData(
                        db.rs.getString("bookID"),
                        db.rs.getString("bname"),
                        db.rs.getString("author"),
                        db.rs.getString("publish"),
                        db.rs.getString("rentID"),
                        db.rs.getString("startDate"),
                        db.rs.getString("enddate"),
                        db.rs.getString("reservationID")
                );

                datas.add(temp);

            }// while
        } catch (SQLException e) {
         
        	//e.printStackTrace();
        }

        db.connectDB();
        /*
         * 이후 DB에서 데이터를 긁어오면 연결을 해제하고 반환을 한다.
         */
        if (!datas.isEmpty())
            return datas;
        else
            return null;

    }//getRentData()


    //bookManagemet
    public ArrayList<BookData> getBookData(String condi) {
    	//서버 UI단에서 책정보를 가져올때 사용하는 메소드
    	db.connectDB();
        String sql = "select book.bookID, bname, author, publish ,rentID, startdate, enddate,reservationID from book left join rent_info on book.bookID = rent_info.bookID left join reservation_info on rent_info.bookID = reservation_info.bookID";
        sql += condi;
        
        ArrayList<BookData> datas = new ArrayList<BookData>();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.rs = db.pstmt.executeQuery();

            while (db.rs.next()) {

                BookData temp = new BookData(
                        db.rs.getString("bookID"),
                        db.rs.getString("bname"),
                        db.rs.getString("author"),
                        db.rs.getString("publish"),
                        db.rs.getString("rentID"),
                        db.rs.getString("startdate"),
                        db.rs.getString("enddate"),
                        db.rs.getString("reservationID")
                );

                datas.add(temp);
            }//while

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();
        if (!datas.isEmpty())
            return datas;
        else
            return null;

        //위에서 설명한것과 같은 논리로 움직인다.
    }//getBookData


    public boolean delBook(String bookId) {
    	//책의 정보를 삭제하는 메소드
        db.connectDB();

        int result = 0;

        String sql = "delete from book where bookID =?";

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, bookId);
            result = db.pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        sql = "delete from rent_Info where bookID =? ";
        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, bookId);
            result = db.pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        sql = "delete from reservation_Info where bookID =? ";
        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, bookId);
            result = db.pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();

        if (result > 0)
            return true;
        else
            return false;
        //책을 삭제할때는 예약정보와 대출정보도 전부 사라지게 해야하므로 이를 모두 구현

    }//delbook()

    public boolean insertBook(BookData book) {
    	//책을 새로 등록하는 메소드
        db.connectDB();
        int result = 0;
        String sql = "insert into book(bookID,bname,author,publish) values (?,?,?,?)";

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, book.getBookID());
            db.pstmt.setString(2, book.getBookName());
            db.pstmt.setString(3, book.getAuthor());
            db.pstmt.setString(4, book.getPublish());
            result = db.pstmt.executeUpdate();
            db.closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (result > 0)
            return true;
        else
            return false;


    }//insertBook()

    public boolean rentBook(BookData book) {
    	//책을 대여하는 메소드
        db.connectDB();
        int result = 0;
        String sql = "insert into rent_info values (?,?,?,?)";

        book.setStartDate();
        book.setEndDate();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, book.getBookID());
            db.pstmt.setString(2, book.getRentUserID());
            db.pstmt.setString(3, book.getStartDate());
            db.pstmt.setString(4, book.getEndDate());
            result = db.pstmt.executeUpdate();
            System.out.println(result);
            db.closeDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result > 0)
            return true;
        else
            return false;


    } // rentBook()

    public boolean returnBook(BookData book) {
    	//책을 반납하는 메소드
        db.connectDB();
        int result = 0;
        String sql = "delete from rent_info where bookID =?";

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, book.getBookID());
            result = db.pstmt.executeUpdate();
            db.closeDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result > 0)
            return true;
        else
            return false;


    } // returnBook()


    public boolean reserveBook(BookData book) {
    	//책을 예약하는 메소드
        db.connectDB();
        int result = 0;

        String sql = "insert into reservation_info(bookID,reservationID) values (?,?)";

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, book.getBookID());
            db.pstmt.setString(2, book.getRentUserID());

            result = db.pstmt.executeUpdate();
            db.closeDB();

        } catch (SQLException e) {
            //e.printStackTrace();
        }

        if (result > 0)
            return true;
        else
            return false;

    } // reserveBook()


    public boolean cancelReserveBook(BookData book) {
    	//책 예약 취소를 하는 메소드
        db.connectDB();
        int result = 0;
        String sql = "delete from reservation_info where bookID =?";

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, book.getBookID());
            result = db.pstmt.executeUpdate();
            db.closeDB();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result > 0)
            return true;
        else
            return false;


    } // cancelReserveBook()
    /*
     * 여기까지가 서버 UI에서 업무를 명령했을때 DB에 접근해서 일을 처리하는 메소드이다.
     */
    
    //client window
    /*
     * 여기서부터는 클라이언트의 JTable에 맞춰서 정보를 효과적으로 전달하기 위해 따로 구현된 메소드이다
     * 이 메소드는 서버단에서 가동되며 정보를 만든 후 클라이언트에게로 전달될때 사용되는 메소드이다.
     */
    public ArrayList<BookData> getbookAll(String condi) {
    	//책을 검색했을때 원하는 정보를 얻기위해 DB에 연결해서 긁어오는 메소드
        db.connectDB();
        String sql = "select * from book ";
        if(condi == null) {
        	sql = "select * from book";
        }
        else {
        	sql += condi;
        }

        ArrayList<BookData> datas = new ArrayList<BookData>();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.rs = db.pstmt.executeQuery();

            while (db.rs.next()) {

                BookData temp = new BookData(
                        db.rs.getString("bookID"),
                        db.rs.getString("bname"),
                        db.rs.getString("author"),
                        db.rs.getString("publish")

                );

                datas.add(temp);
            }//while

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();
        if (!datas.isEmpty())
            return datas;
        else
            return null;


    }

    // rent_info
    public ArrayList<BookData> getrentAll(String condi) { // condi (condi = 'where rentId = ???')
        //현재 어떤 사용자가 대여한 책의 목록만을 받아오기 위한 메소드
    	db.connectDB();
        String sql = "select book.bookID, bname, author, publish , startdate, enddate from book left join rent_info on book.bookID = rent_info.bookID";

        sql += condi;
        ArrayList<BookData> datas = new ArrayList<BookData>();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.rs = db.pstmt.executeQuery();

            while (db.rs.next()) {

                BookData temp = new BookData(
                        db.rs.getString("bookID"),
                        db.rs.getString("bname"),
                        db.rs.getString("author"),
                        db.rs.getString("publish"),
                        db.rs.getString("startdate"),
                        db.rs.getString("enddate")

                );

                datas.add(temp);
            }//while

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();
        if (!datas.isEmpty())
            return datas;
        else
            return null;


    }
    
    public ArrayList<BookData> getLoginReservedData(String condi) {
    	//현재 어떤 로그인한 사용자가 예약한 책의 목록만을 긁어오기 위해 구현된 메소드
    	db.connectDB();
    	String sql = "select book.bookID, bname, author, publish from book left join reservation_info on book.bookID = reservation_info.bookID";
    	sql+=condi;
    	
    	ArrayList<BookData> datas = new ArrayList<BookData>();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.rs = db.pstmt.executeQuery();

            while (db.rs.next()) {

                BookData temp = new BookData(
                        db.rs.getString("bookID"),
                        db.rs.getString("bname"),
                        db.rs.getString("author"),
                        db.rs.getString("publish")
                );

                datas.add(temp);
            }//while

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();
        if (!datas.isEmpty())
            return datas;
        else
            return null;
    }

}
