package DataStructure;

public class UserData {

	/*
	 * ����� ������ ���� �ڷᱸ��
	 * ���̵� ��й�ȣ ����� �̸� �׸��� �α��� ���� 4������ �����Ǿ� �ִ�.
	 */
	private String userID;
	private String userPassword;
	private String userName;
	private boolean flag;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public UserData(String userID, String userPassword, String userName, boolean flag) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userName = userName;
		this.flag = flag;
	}

	public UserData(String userID, String userPassword, String userName) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userName = userName;
	}

	public UserData() {
		this.userID = null;
		this.userPassword = null;
		this.userName = null;
		this.flag = false;
	}



}
