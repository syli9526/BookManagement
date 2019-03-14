package DataStructure;

public class MessageData {
	/*
	 * �����͸� �Ľ��ϱ��� ����� �޽��� �ڷᱸ��
	 * ����� �ؾ��ϴ��� �˷��ִ� type�� å���� ����� ���� 3������ ����ִ�.
	 */
	private String type;
	private UserData userInfo;
	private BookData bookInfo;

	@Override
	public String toString() {
		return "MessageData [type=" + type + ", userInfo=" + userInfo + ", bookInfo=" + bookInfo + "]";
	}
	//� ������ ���ƿ����� Ȯ���ϱ� ���� ������ toString�޼ҵ�

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
