package DAO;

import DataStructure.UserData;
import Server.ServerConstant;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserInfoDAO {
	/*
	 * ����� ������ ���� ������ �ٷ�� DAO�̴�.
	 * �⺻���� ������ ������ BookInfoDAO ���� ������ �κа� ������ �ܷ�Ű�� ����� sql���� ���⶧���� ���������
	 * �ſ� ���� �κ��̴�.
	 * ���⿡ ������ ������� ���� ���� UI�κп��� ���Ǵ� �͵��̸� login�� ���õ� 2���� �޼ҵ常 Ŭ���̾�Ʈ���� ���� ��û�� 
	 * �ҋ� ������ �޾ƿͼ� ó�����ִ� �κ��̴�.
	 */

    private LinkDB db;

    public UserInfoDAO() {

        db = new LinkDB();

    }

    public ArrayList<UserData> getUser(String condi) {
    	//���Ե� ������� ������ ���� �������� �޼ҵ�
        db.connectDB();

        String sql = "select * from user";
        sql += condi;

        ArrayList<UserData> datas = new ArrayList<UserData>();

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.rs = db.pstmt.executeQuery();

            while (db.rs.next()) {
                //public UserData(String userID, String userPassword, String userName, boolean flag)
                UserData temp = new UserData(db.rs.getString("userID"),
                        db.rs.getString("password"),
                        db.rs.getString("uname"),
                        db.rs.getBoolean("flag"));

                datas.add(temp);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();

        if (!datas.isEmpty())
            return datas;
        else
            return null;

    }//getUser()


    public boolean delUser(String id) {
    	//���Ե� ������� ������ �����ϴ� �޼ҵ�
        db.connectDB();

        int result = 0;
        String sql = "delete from user where userID =?";
        UserData user = null;

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, id);
            result = db.pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();
        if (result > 0)
            return true;
        else
            return false;


    }//delUser()

    //public UserData(String userID, String userPassword, String userName, boolean flag)
    public boolean insertUser(UserData user) {
    	//����� ������ ����ϴ� �޼ҵ�
        db.connectDB();
        int result = 0;
        String sql = "insert into user values (?,?,?,0)";

        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, user.getUserID());
            db.pstmt.setString(2, user.getUserPassword());
            db.pstmt.setString(3, user.getUserName());
            result = db.pstmt.executeUpdate();
            db.closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);

        if (result > 0)
            return true;
        else
            return false;


    }//insertUser()

    public boolean upDateUser(UserData user) {
    	//����� ������ �����ϴ� �޼ҵ�
        db.connectDB();
        int result = 0;

        String sql = "update user set userID =?, password =? ,uname =? , flag=?";


        try {
            db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setString(1, user.getUserID());
            db.pstmt.setString(2, user.getUserPassword());
            db.pstmt.setString(3, user.getUserName());
            db.pstmt.setBoolean(4, user.isFlag());

            result = db.pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDB();
        if (result > 0)
            return true;
        else
            return false;

    }//updateUser()
    
    /*
     * Ŭ���̾�Ʈ�� ��û������ ó�� �κ�
     */
    public boolean loginCheck(UserData userData) {
    	//�α����� ���������� ���θ� üũ���ִ� �޼ҵ�
    	db.connectDB();
        String id = userData.getUserID();
        String pw = userData.getUserPassword();
        String password;
        boolean flag = true;
        String sql = "select * from user where userID = ?";
        //String sql = "update user set userID =?, password =? ,uname =? , flag=?";

        try {
        	db.pstmt = db.conn.prepareStatement(sql);
        	db.pstmt.setString(1, id);
        	db.rs = db.pstmt.executeQuery();
        	db.rs.next();
        	password = db.rs.getString("password");
        	//System.out.println(pw);
        	//System.out.println(password);
        	if(pw.compareTo(password) == 0) {
        		//����� ������ ã�ƿͼ� �� ���� ��й�ȣ�� �Է��ؼ� ������ ��й�ȣ�� ��ġ�ϴٸ�
        		sql = "update user set flag=? where userID =?";
        		db.pstmt = db.conn.prepareStatement(sql);
            	db.pstmt.setBoolean(1, flag);
            	db.pstmt.setString(2, id);
            	db.pstmt.executeUpdate();
            	return true;
            	//�α��� ������ DB�� ���뺯���� �� true�� ��ȯ
        	}
        	else {
        		return false;
        		//�����ϸ� false�� ��ȯ
        	}
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        	//����ó��
        }
        
    }//loginCheck()
    
    public boolean logoutCheck(UserData userData) {
    	//�α׾ƿ��� ��û�Ҷ� ����ϴ� �޼ҵ�
    	db.connectDB();
        String id = userData.getUserID();
        boolean flag = false;
        String sql = "update user set flag=? where userID = ?";
        //String sql = "update user set userID =?, password =? ,uname =? , flag=?";

        try {
        	db.pstmt = db.conn.prepareStatement(sql);
            db.pstmt.setBoolean(1, flag);
            db.pstmt.setString(2, id);
            db.pstmt.executeUpdate();
        	return true;
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        return false;
    }//logoutCheck()


}//UserInfoDAO
