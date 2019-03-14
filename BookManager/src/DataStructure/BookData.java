package DataStructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BookData {

	/*
	 * å�� ���õ� �ڷᱸ��
	 */
    private String bookID;
    //å ���̵�
    private String bookName;
    //å�̸�
    private String author;
    //å ����
    private String publish;
    //���ǻ�
    private String rentUserID;
    //�뿩 ����� ���̵�
    private String reservationUserID;
    //���� ����� ���̵�
    private String startDate;
    //�뿩��
    private String endDate;
    //�ݳ� ������

    public BookData() {
    }

    @Override
	public String toString() {
		return "BookData [bookID=" + bookID + ", bookName=" + bookName + ", author=" + author + ", publish=" + publish
				+ ", rentUserID=" + rentUserID + ", reservationUserID=" + reservationUserID + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

    /*
     * �ʿ信 ���� ������ ��Ű¡�� �޶��� ������ �̿� ���� �����ڸ� �Ķ���� ���� �����ؼ� ����
     */
	public BookData(String bookID, String rentUserID) {
        this.bookID = bookID;
        this.rentUserID = rentUserID;
    }
	//���� ���ɿ��ε��� ������ ����ϴ� ������

    public BookData(String bookID, String bookName, String author, String publish) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publish = publish;
    }
    //�˻� ������ ������ ���� ������

    public BookData(String bookID, String bookName, String author, String publish, String startDate, String endDate) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publish = publish;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    //�뿩 ������ ������ ���� ������

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
     * get set �޼ҵ��
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
    	//�������� �����ϴ� �޼ҵ� --> ��ǻ�� �ð��� �޾ƿͼ� ����
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        startDate = fm.format(calendar.getTime());
    }

    public String getEndDate() {

        return endDate;
    }

    public void setEndDate() {
    	//�ݳ��������� �����ϴ� �޼ҵ� �ݳ� �������� 7�� �ķ� �����ߴ�.
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        endDate = fm.format(calendar.getTime());
    }

}
