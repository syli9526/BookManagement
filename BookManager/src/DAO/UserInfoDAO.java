package DAO;

import DataStructure.UserData;
import Server.ServerConstant;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserInfoDAO {
	/*
	 * 사용자 정보에 대한 접근을 다루는 DAO이다.
	 * 기본적인 구현의 내용은 BookInfoDAO 에서 설명한 부분과 같으며 외래키를 사용한 sql문은 없기때문에 상대적으로
	 * 매우 쉬운 부분이다.
	 * 여기에 구현된 내용들은 전부 서버 UI부분에서 사용되는 것들이며 login에 관련된 2개의 메소드만 클라이언트에서 접근 요청을 
	 * 할떄 서버가 받아와서 처리해주는 부분이다.
	 */

    private LinkDB db;

    public UserInfoDAO() {

        db = new LinkDB();

    }

    public ArrayList<UserData> getUser(String condi) {
    	//가입된 사용자의 내용을 전부 가져오는 메소드
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
    	//가입된 사용자의 정보를 삭제하는 메소드
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
    	//사용자 정보를 등록하는 메소드
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
    	//사용자 정보를 갱신하는 메소드
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
     * 클라이언트가 요청했을때 처리 부분
     */
    public boolean loginCheck(UserData userData) {
    	//로그인을 인정해줄지 여부를 체크해주는 메소드
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
        		//사용자 정보를 찾아와서 이 행의 비밀번호가 입력해서 보내준 비밀번호와 일치하다면
        		sql = "update user set flag=? where userID =?";
        		db.pstmt = db.conn.prepareStatement(sql);
            	db.pstmt.setBoolean(1, flag);
            	db.pstmt.setString(2, id);
            	db.pstmt.executeUpdate();
            	return true;
            	//로그인 중으로 DB에 내용변경한 후 true를 반환
        	}
        	else {
        		return false;
        		//실패하면 false를 반환
        	}
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        	//예외처리
        }
        
    }//loginCheck()
    
    public boolean logoutCheck(UserData userData) {
    	//로그아웃을 요청할때 사용하는 메소드
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
