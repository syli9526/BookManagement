package DataStructure;

public class MessageData {
	/*
	 * 데이터를 파싱하기전 만드는 메시지 자료구조
	 * 어떤일을 해야하는지 알려주는 type과 책정보 사용자 정보 3가지로 들어있다.
	 */
	private String type;
	private UserData userInfo;
	private BookData bookInfo;

	@Override
	public String toString() {
		return "MessageData [type=" + type + ", userInfo=" + userInfo + ", bookInfo=" + bookInfo + "]";
	}
	//어떤 정보가 날아오는지 확인하기 위해 그현한 toString메소드

	public MessageData(){

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserData getUserData() {
		return userInfo;
	}

	public void setUserData(UserData userInfo) {
		this.userInfo = userInfo;
	}

	public BookData getBookData() {
		return bookInfo;
	}

	public void setBookData(BookData bookInfo) {
		this.bookInfo = bookInfo;
	}
}
