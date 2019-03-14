package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LinkDB {
	/*
	 * DB에 연결하고 DB접근시 사용하는 pstmt conn rs등의 변수를 가지고 있는 클래스 
	 */
	public Connection conn;
	public PreparedStatement pstmt;
	public ResultSet rs;
	private DBInfo user;

	public LinkDB() {
		user = new DBInfo("root", "thdwkdrms12!@", "jdbc", "8800");
		//DB연결시 필요한 정보를 LinkDB() 생성자를 통해 바로 구현한다. 즉 DB에 연결하기 위해서는 여기 부분의 수정이 필수
		//이다.
	}

	public void connectDB() {
		//DB접근하기 전 연결 메소드
		try {
			Class.forName(user.getJdbcDriver()).newInstance();
			conn = DriverManager.getConnection(user.getJdbcUrl(), user.getID(), user.getPassword());
			System.out.println("connect success");

		} catch (Exception e) {
			e.printStackTrace();
		}
	} //connectDB()

    public void closeDB() {
    	//DB에서의 모든 일이 끝나고 난 후 연결을 닫는 메소드
        try {
        	if(pstmt != null) {
        		pstmt.close();
        	}
        	if(rs != null) {
        		rs.close();
        	}
        	if(conn != null) {
        		conn.close();
        	}
        	//3개의 접근 변수가 null일 수도 있을때 예외상황을 막기위한 if문
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// closeDB
}
