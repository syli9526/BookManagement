package DAO;

public class DBInfo {
	/*
	 * MySQL을 연결하기 위해 필요한 기초적인 4가지 정보를 미리 정의해두는 자료구조
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
		//파라미터 없이 생성했을때 자동으로 설정되는 정보들 --> 필요하면 set메소드를 통해 정보 수정 필수
	}
	
	 public DBInfo(String iD, String password, String DBname) {
	      super();
	      jdbcDriver = "com.mysql.cj.jdbc.Driver";
	      this.ID = iD;
	      this.password = password;
	      jdbcUrl = "jdbc:mysql://localhost:3306/" + DBname + "?characterEncoding=UTF-8&&serverTimezone=UTC";
	      /*
	       * DB의 포트번호는 3306이고 다른것만 모두 다를때 사용하는 생성자
	       */
	 }
	   
	   public DBInfo(String iD, String password, String DBname, String PortNumber) {
	      super();
	      jdbcDriver = "com.mysql.cj.jdbc.Driver";
	      this.ID = iD;
	      this.password = password;
	      jdbcUrl = "jdbc:mysql://localhost:" + PortNumber + "/" + DBname + "?characterEncoding=UTF-8&&serverTimezone=UTC";
	      //DB포트번호까지 사용자의 임의로 변경했을때를 대비한 생성자
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
	 * DB연결 정보에 대한 get/set 메소드들
	 */

}
