package Server;

public class ServerConstant {
	/*
	 * 서버에 UI에 필요한 각종 정보들을 상수로 저장하고 있는 클래스
	 */
    public static final String M_BOOK = "Book Manager";
    public static final String M_USER = "client Manager";
    public static final String M_RESERVATION = "Reservation Manager";
    public static final String M_RENT = "Rent Manager";

    public static final String[] USER_COMBO = {"ID", "client name"};
    public static final String[] BOOK_INFO = {"bookID", "book name", "author", "publish", "rentID", "startDate", "endDate", "reservationID"};
    public static final String[] USER_INFO = {"userID", "password", "client name", "isLogin"};
    public static final String[] RENT_INFO = {"bookID", "book name", "author", "publish", "rentID", "startDate", "endDate"};
    public static final String[] RESERVATION_INFO = {"bookID", "book name", "author", "publish", "rentID", "startDate", "endDate", "reservationID"};
    public static final String[] RESERVATION_TYPE = {"reserved", "reservable"};
    public static final String SERACH = "SERACH";
    public static final String INSERT = "INSERT";
    public static final String DELETE = "DELETE";
    public static final String RENTAL = "RENTAL";
    public static final String UPDATE = "UPDATE";
    public static final String RETURN = "RETURN";
    public static final String ENTER ="ENTER";
    public static final String RESERVATION = "RESERVATION";
    public static final String CANCEL = "CANCEL";

    //sql
    public static final String[] BOOK ={"book.bookID","bname","author","publish","rentID","startdate","enddate","reservationID"};
    public static final String[] USER ={"userID","password","uname","flag"};

}