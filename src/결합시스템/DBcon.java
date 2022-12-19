package 결합시스템;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBcon {
    String driver = "org.mariadb.jdbc.Driver";
    Connection con;
    ResultSet rs;
    Statement st;


    public DBcon() {
    	try {

            Class.forName(driver);
            con = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/project_test",
                    "a05",
                    "1q2w3e4r7&");
            
            /*
            if( con != null ) {
                System.out.println("DB 접속 성공");
            }
            */
            
    	}
    	catch (ClassNotFoundException e) { 
	            System.out.println("드라이버 로드 실패");
	    } 
		catch (SQLException e) {
	            System.out.println("DB 접속 실패");
	            e.printStackTrace();
		}
    }
            
    public void DBdiscon() {
    	try {
    		rs.close();
    		st.close();
    		con.close();
    	}
    	catch (SQLException e) {
            System.out.println("DB 접속 해제 실패");
            e.printStackTrace();
    	}
    }
}

