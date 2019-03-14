package Client;

public class ClientConstant {
	/*
	 * 클라이언트 부분에서 사용하는 여러가지 문장들을 상수처럼 부르기 위해 만들어둔 상수 자료구조
	 */
	public static final String LOGIN = "LOGIN"; 
	public static final String LOGOUT = "LOGOUT"; 
	public static final String REGIST = "REGIST"; 
	public static final String SEARCH = "SEARCH";
	public static final String RESERVE = "RESERVE"; 
	//각 버튼의 이름을 상수처리
	
	public static final String bookInfoheader[] = {"bookID", "book name", "author", "publish", "startDate", "endDate"};
	public static final String bookReserveInfoheader[] = {"bookID", "book name", "author", "publish"};
	public static final String comboBoxStirng[] = {"All", "book name", "author", "publish"};
	//JTable에 들어갈 표의 상단부 구분 부분을 상수처리
	
}
