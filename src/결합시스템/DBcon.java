package 결합시스템;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBcon {
  
    Connection con;
    ResultSet rs;
    Statement st;
    static String db_add ="jdbc:mariadb://localhost:3306/DB" ;
    static String db_name ="root";
    static String db_pswd ="1111";
    static String db_start="org.mariadb.jdbc.Driver";


    public DBcon() {
    	try {

            Class.forName(db_start);
            con = DriverManager.getConnection(
                    db_add,
                    db_name,
                    db_pswd);
            
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

