package DAO;

public class DBInfo {
	/*
	 * MySQL�� �����ϱ� ���� �ʿ��� �������� 4���� ������ �̸� �����صδ� �ڷᱸ��
	 */
	private String jdbcDriver ;
	private String jdbcUrl;
	private String ID;
	private String password;

	public DBInfo() {
		jdbcDriver = "com.mysql.cj.jdbc.Driver";
		jdbcUrl = "jdbc:mysql://localhost:3306/javaDB ?characterEncoding=UTF-8&&serverTimezone=UTC";
		ID = "root";
		password = "11111111";
		//�Ķ���� ���� ���������� �ڵ����� �����Ǵ� ������ --> �ʿ��ϸ� set�޼ҵ带 ���� ���� ���� �ʼ�
	}
	
	 public DBInfo(String iD, String password, String DBname) {
	      super();
	      jdbcDriver = "com.mysql.cj.jdbc.Driver";
	      this.ID = iD;
	      this.password = password;
	      jdbcUrl = "jdbc:mysql://localhost:3306/" + DBname + "?characterEncoding=UTF-8&&serverTimezone=UTC";
	      /*
	       * DB�� ��Ʈ��ȣ�� 3306�̰� �ٸ��͸� ��� �ٸ��� ����ϴ� ������
	       */
	 }
	   
	   public DBInfo(String iD, String password, String DBname, String PortNumber) {
	      super();
	      jdbcDriver = "com.mysql.cj.jdbc.Driver";
	      this.ID = iD;
	      this.password = password;
	      jdbcUrl = "jdbc:mysql://localhost:" + PortNumber + "/" + DBname + "?characterEncoding=UTF-8&&serverTimezone=UTC";
	      //DB��Ʈ��ȣ���� ������� ���Ƿ� ������������ ����� ������
	   }


	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	/*
	 * DB���� ������ ���� get/set �޼ҵ��
	 */

}
