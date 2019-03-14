package DAO;

import DataStructure.BookData;
import DataStructure.UserData;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookInfoDAO {
    private LinkDB db;

    /*
     * å ������ DB�� �����ؼ� �޾ƿ��� �����ϴ� ���� ��� ����ϴ� DAO Ŭ����
     */
    
    public BookInfoDAO() {

        db = new LinkDB();
        //�����ڿ��� DB�� DAO�� �����ϴ� ���� �۾��� �����Ѵ�.
    }//BookInfoDAO()

    //rent
    public ArrayList<BookData> getRentData(String condi) {
    	//�뿩�� å���� �庸���� ��ƿͼ� ArrayList�� �����ϴ� �޼ҵ�
        db.connectDB();
        //�켱 DB�� ������ �ϰ�
        String sql = "select book.bookID, bname, author, publish ,rentID, startdate, enddate from book left join rent_info on book.bookID = rent_info.bookID";
        //sql���� �ʹ� �����ϴµ� ���� ������ ���� ������ ���̺��� ���� �����ؼ� �� ������ ���̰� �ϱ� ���ؼ���
        //�ܷ�Ű�� ����� ������ �޾ƿ;� �Ѵ�. �̸� ���� �ʹ� DB�� ���̺��� �����Ҷ� �ܷ�Ű�� �̿��ؿ���.
        sql += condi;
        //�� sql �ڿ� �߰��� � ������ ���� �˻��Ұ��� �� ���ڿ��� �߰��� �Ķ���ͷ� ���� �����ȴ�.
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
         * DB�� �����Ͽ� sql���� ������ ���� ����� �� ����
         */
        
        db.connectDB();
        //DB������ ����
        if (!datas.isEmpty())
            return datas;
        else
            return null;
        //ArrayList�� null�� �ƴ϶�� ��ȯ�ϰ� null�̸� null�� ��ȯ�Ѵ�.
    }//getRentData()

    //reservation
    public ArrayList<BookData> getReservationData(String condi) {
    	//���� ������ DB�� ���� �޾ƿ��� �޼ҵ�
        db.connectDB();
        String sql = "select book.bookID, bname, author, publish ,rentID, startdate, enddate, reservationID from book left join rent_info on book.bookID = rent_info.bookID left join reservation_info on rent_info.bookID = reservation_info.bookID";

        if(condi == null) {
        	sql = "select book.bookID, bname, author, publish ,rentID, startdate, enddate, reservationID from book left join rent_info on book.bookID = rent_info.bookID left join reservation_info on rent_info.bookID = reservation_info.bookID where reservation_info.bookID is not null and rent_info.bookID is not null";
        	//��� where ���� �Ķ���͵� ���ٸ� �켱 ���� UI�ܿ��� �޺� �ڽ��� ����� å�� ���� ��쵵�� �����ϱ� ���� �� sql
        	//��ɹ��� �����ϰ�
        }
        else {
        	sql += condi;
        	//�Ķ���Ͱ� ���� ������ �װ� ���ؼ� �˻������� �����.
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
         * ���� DB���� �����͸� �ܾ���� ������ �����ϰ� ��ȯ�� �Ѵ�.
         */
        if (!datas.isEmpty())
            return datas;
        else
            return null;

    }//getRentData()


    //bookManagemet
    public ArrayList<BookData> getBookData(String condi) {
    	//���� UI�ܿ��� å������ �����ö� ����ϴ� �޼ҵ�
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

        //������ �����ѰͰ� ���� ���� �����δ�.
    }//getBookData


    public boolean delBook(String bookId) {
    	//å�� ������ �����ϴ� �޼ҵ�
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
        //å�� �����Ҷ��� ���������� ���������� ���� ������� �ؾ��ϹǷ� �̸� ��� ����

    }//delbook()

    public boolean insertBook(BookData book) {
    	//å�� ���� ����ϴ� �޼ҵ�
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
    	//å�� �뿩�ϴ� �޼ҵ�
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
    	//å�� �ݳ��ϴ� �޼ҵ�
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
    	//å�� �����ϴ� �޼ҵ�
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
    	//å ���� ��Ҹ� �ϴ� �޼ҵ�
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
     * ��������� ���� UI���� ������ ��������� DB�� �����ؼ� ���� ó���ϴ� �޼ҵ��̴�.
     */
    
    //client window
    /*
     * ���⼭���ʹ� Ŭ���̾�Ʈ�� JTable�� ���缭 ������ ȿ�������� �����ϱ� ���� ���� ������ �޼ҵ��̴�
     * �� �޼ҵ�� �����ܿ��� �����Ǹ� ������ ���� �� Ŭ���̾�Ʈ���Է� ���޵ɶ� ���Ǵ� �޼ҵ��̴�.
     */
    public ArrayList<BookData> getbookAll(String condi) {
    	//å�� �˻������� ���ϴ� ������ ������� DB�� �����ؼ� �ܾ���� �޼ҵ�
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
        //���� � ����ڰ� �뿩�� å�� ��ϸ��� �޾ƿ��� ���� �޼ҵ�
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
    	//���� � �α����� ����ڰ� ������ å�� ��ϸ��� �ܾ���� ���� ������ �޼ҵ�
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
