package DataStructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BookData {

	/*
	 * 책에 관련된 자료구조
	 */
    private String bookID;
    //책 아이디
    private String bookName;
    //책이름
    private String author;
    //책 저자
    private String publish;
    //출판사
    private String rentUserID;
    //대여 사용자 아이디
    private String reservationUserID;
    //예약 사용자 아이디
    private String startDate;
    //대여일
    private String endDate;
    //반납 예정일

    public BookData() {
    }

    @Override
	public String toString() {
		return "BookData [bookID=" + bookID + ", bookName=" + bookName + ", author=" + author + ", publish=" + publish
				+ ", rentUserID=" + rentUserID + ", reservationUserID=" + reservationUserID + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

    /*
     * 필요에 따른 데이터 패키징이 달랐기 때문에 이에 맞춰 생성자를 파라미터 별로 분해해서 설정
     */
	public BookData(String bookID, String rentUserID) {
        this.bookID = bookID;
        this.rentUserID = rentUserID;
    }
	//예약 가능여부등을 받을때 사용하는 생성자

    public BookData(String bookID, String bookName, String author, String publish) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publish = publish;
    }
    //검색 정보를 받을때 쓰는 생성자

    public BookData(String bookID, String bookName, String author, String publish, String startDate, String endDate) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publish = publish;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    //대여 정보를 받을때 쓰는 생성자

    //bookpanel
    public BookData(String bookID, String bookName, String author, String publish, String rentUserID, String reservationUserID, String startDate, String endDate) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publish = publish;
        this.rentUserID = rentUserID;
        this.reservationUserID = reservationUserID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    
    //rental,reservatioPanel
    public BookData(String bookID, String bookName, String author, String publish, String rentUserID, String startDate, String endDate) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publish = publish;
        this.rentUserID = rentUserID;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    /*
     * get set 메소드들
     */
    public BookData getBookData() {
    	return this;
    }
    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getRentUserID() {
        return rentUserID;
    }

    public void setRentUserID(String rentUserID) {
        this.rentUserID = rentUserID;
    }

    public String getReservationUserID() {
        return reservationUserID;
    }

    public void setReservationUserID(String reservationUserID) {
        this.reservationUserID = reservationUserID;
    }

    public String getStartDate() {

        return startDate;
    }

    public void setStartDate() {
    	//대출일을 설정하는 메소드 --> 컴퓨터 시간을 받아와서 설정
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        startDate = fm.format(calendar.getTime());
    }

    public String getEndDate() {

        return endDate;
    }

    public void setEndDate() {
    	//반납예정일을 설정하는 메소드 반납 예정일은 7일 후로 설정했다.
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        endDate = fm.format(calendar.getTime());
    }

}
