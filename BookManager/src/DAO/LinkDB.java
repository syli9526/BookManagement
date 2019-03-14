package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LinkDB {
	/*
	 * DB�� �����ϰ� DB���ٽ� ����ϴ� pstmt conn rs���� ������ ������ �ִ� Ŭ���� 
	 */
	public Connection conn;
	public PreparedStatement pstmt;
	public ResultSet rs;
	private DBInfo user;

	public LinkDB() {
		user = new DBInfo("root", "thdwkdrms12!@", "jdbc", "8800");
		//DB����� �ʿ��� ������ LinkDB() �����ڸ� ���� �ٷ� �����Ѵ�. �� DB�� �����ϱ� ���ؼ��� ���� �κ��� ������ �ʼ�
		//�̴�.
	}

	public void connectDB() {
		//DB�����ϱ� �� ���� �޼ҵ�
		try {
			Class.forName(user.getJdbcDriver()).newInstance();
			conn = DriverManager.getConnection(user.getJdbcUrl(), user.getID(), user.getPassword());
			System.out.println("connect success");

		} catch (Exception e) {
			e.printStackTrace();
		}
	} //connectDB()

    public void closeDB() {
    	//DB������ ��� ���� ������ �� �� ������ �ݴ� �޼ҵ�
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
        	//3���� ���� ������ null�� ���� ������ ���ܻ�Ȳ�� �������� if��
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// closeDB
}
