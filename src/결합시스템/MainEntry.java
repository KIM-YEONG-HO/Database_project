package 결합시스템;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;	




public class MainEntry {
	
	static String db_add = DBcon.db_add ;
	static String db_name = DBcon.db_name;
	static String db_pswd = DBcon.db_pswd;
	static String db_start= DBcon.db_start;
	
	
	public String showScanned() {
		
		String SQL = "SELECT a.name AS \'테이블명\', a.num_record AS \'레코드수\', count(b.속성명) "
				   	+"FROM table_info AS a, c_info AS b "
				   	+"WHERE a.name = b.테이블명 AND " //두 테이블 조인한 후,
				   	+"a.done =\'Y\' " //속성 도메인 스캔이 완료된 테이블만
				   	+"GROUP BY a.name";
		return SQL;
	}
	
	public String showNotScanned() {

		String SQL = "SELECT a.name AS \'테이블명\', a.num_record AS \'레코드수\', count(b.속성명) "
			   	+"FROM table_info AS a, c_info AS b "
			   	+"WHERE a.name = b.테이블명 AND " //두 테이블 조인한 후,
			   	+"a.done =\'N\' " //속성 도메인 스캔이 완료된 테이블만
			   	+"GROUP BY a.name";
		return SQL;
	}
	public String showNumeric(String tablename) {
		String SQL = "SELECT ci.속성명, ci.데이터타입, ci.NULL레코드수, ci.NULL레코드비율, cn.상이수치값, cn.최대값, cn.최소값, cn.ZERO레코드수, cn.ZERO레코드비율, ci.대표속성, ci.결합키후보, ci.대표결합키"
					+"FROM c_info as ci, c_numeric as cn"
					+"WHERE ci.속성명 = cn.속성명 AND cn.테이블명 = \'" + tablename + "\'";
		return SQL;
	}
	
	public String showCategory(String tablename) {
		String SQL = "SELECT ci.속성명, ci.데이터타입, ci.NULL레코드수, ci.NULL레코드비율, cc.상이범주값, cc.특수문자포함레코드수, cc.특수문자포함레코드비율, ci.대표속성, ci.결합키후보, ci.대표결합키"
					+"FROM c_info as ci, c_category as cc"
					+"WHERE ci.속성명 = cc.속성명 AND cc.테이블명 = \'" + tablename + "\'";
		return SQL;
	}
	
	public static <T extends Integer> ArrayList  scanlistmaker_n(ArrayList<T> o,int typenum,String typename,String tablename)
		{//수치속성
			int null_record=0;
			int distinct_num=o.size();
			T max_value=null;
			T min_value=null;
			int zero_record = 0;
			String unionkey_recommend = "X";
			for(int n=0; n<o.size(); n++)
				{if(o.get(n)==null)
				{null_record++;}
				else if(o.get(n)==0)
					{zero_record++;}
				if(n==0){max_value = o.get(n);}
				else if(max_value<o.get(n)) {max_value = o.get(n);}
				if(n==0){min_value = o.get(n);}
				else if(min_value>o.get(n)) {min_value = o.get(n);}
				for(int m=n+1; m<o.size(); m++)
				{if(o.get(n)==o.get(m)) {}
				}
			
			}
			Set distinct_set = new HashSet<>(o);
			distinct_num=distinct_set.size();
			ArrayList result = new ArrayList();
			if(distinct_num/o.size()>0.9) {unionkey_recommend="O";} else {unionkey_recommend="X";};
			result.add(typename);
			result.add(gettype(typenum));
			result.add(null_record);
			float null_ratio = null_record/o.size();
			result.add(null_ratio);
			result.add(distinct_num);
			result.add(max_value);
			result.add(min_value);
			result.add(zero_record);
			float zero_ratio = zero_record/o.size();
			result.add(zero_ratio);
			result.add(null);
			result.add(unionkey_recommend);
			result.add(null);
			result.add(tablename);
			return result;}
		
	public static <T extends String> ArrayList  scanlistmaker_s(ArrayList<T> o,int typenum,String typename,String tablename)
		{//범주속성
			String pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\\s]*$";
			int null_record=0;
			int distinct_num=o.size();
			int special_record = 0;
			String unionkey_recommend = "X";
			for(int n=0; n<o.size(); n++)
				{if(o.get(n)==null)
				{null_record++;}
				else if(!Pattern.matches(pattern,o.get(n)))
					{special_record++;}
				
				for(int m=n+1; m<o.size(); m++)
				{if(o.get(n).equals(o.get(m))) {distinct_num--;}}

			
			}
			Set distinct_set = new HashSet<>(o);
			distinct_num=distinct_set.size();
			ArrayList result = new ArrayList();
			
			if(distinct_num/o.size()>0.9) {unionkey_recommend="O";} else {unionkey_recommend="X";};
			result.add(typename);
			result.add(gettype(typenum));
			result.add(null_record);
			float null_ratio = null_record/o.size();
			result.add(null_ratio);
			result.add(distinct_num);
			result.add(special_record);
			float special_ratio = special_record/o.size();
			result.add(special_ratio);
			result.add(null);
			result.add(unionkey_recommend);
			result.add(null);
			result.add(tablename);
			return result;}
		
	public static ArrayList scanlistmaker(ArrayList o,int typenum,String typename,String tablename)
		{if(typenum==4)
		{return scanlistmaker_n(o,typenum,typename,tablename);
		}
		else return scanlistmaker_s(o,typenum,typename,tablename);}
	
	public static void getcolumntypefunction(int n, ResultSet m,ArrayList o,ArrayList p) throws SQLException {
		int N = (int) o.get(n-1);
		switch(N) {
		case 4: p.add(m.getInt(n));
		break;
		case 12: p.add(m.getString(n));
		break;
		case 91: p.add(m.getDate(n));
		break;
		default: p.add(m.getString(n));
		break;}}
	
	public static String gettype(int n) {
		switch(n) {
		case 4: return "Integer";
		case 12: return "String";
		case 91: return "Date";
		default: return "String";}
	}

	public static void tablescan() throws SQLException
	{Connection con = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;   
	ResultSet rs = null;
	ResultSet rstemp = null;
	con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	int rs2 = 0;pstmt = con.prepareStatement("select * from table_info");
    
	try {
	    Class.forName(db_start);
	    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	    stmt = con.createStatement();
	    rs = stmt.executeQuery("Show tables");
	    int num=0;
	    while(rs.next()){ 
	    	if(rs.getString(1).equals("c_numeric") || rs.getString(1).equals("c_info")|| rs.getString(1).equals("c_category") ||rs.getString(1).equals("representative_dictionary")||rs.getString(1).equals("standard_dictionary")||rs.getString(1).equals("table_info") || rs.getString(1).equals("결합결과")) {}
	    	else {
	    		String SQL = "SELECT count(*) from " + rs.getString(1);
	    		stmt = con.createStatement();
	    		rstemp=stmt.executeQuery(SQL);
	    		if(rstemp.next()) {
	    			num = rstemp.getInt(1);
	    		}
	    		rs2 = stmt.executeUpdate("INSERT INTO table_info(name, num_record) SELECT '"+rs.getString(1)+"', "+num+" FROM DUAL\r\n"
		    			+ "	 WHERE NOT EXISTS (SELECT * FROM table_info WHERE name = '"+rs.getString(1)+"');");
	    	}
	    	

	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
	        if(rs != null) {            rs.close(); /* 선택 사항*/        }
	        if(stmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(pstmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(con != null) {con.close(); /* 필수 사항 */        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	}
	public static ArrayList showtable() throws SQLException
	{Connection con = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;   
	ResultSet rs = null;
	ArrayList result = new ArrayList();
	con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	pstmt = con.prepareStatement("select * from table_info");
    
	try {
	    Class.forName(db_start);
	    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	    stmt = con.createStatement();
	    rs = pstmt.executeQuery("select * from table_info");
	    int num=0;
	    while(rs.next())
	    { if(rs.getInt("num_record")!=2)
	    	result.add(rs.getString(1));

	    		    
	    
	    
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
	        if(rs != null) {            rs.close(); /* 선택 사항*/        }
	        if(stmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(pstmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(con != null) {con.close(); /* 필수 사항 */        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	return result;
	}
	public static ArrayList showscannedtable() throws SQLException
	{Connection con = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;   
	ResultSet rs = null;
	ArrayList result = new ArrayList();
	con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	pstmt = con.prepareStatement("select * from table_info");
    
	try {
	    Class.forName(db_start);
	    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	    stmt = con.createStatement();
	    rs = pstmt.executeQuery("select * from table_info");
	    int num=0;
	    while(rs.next())
	    { if(rs.getInt("num_record")==1)
	    	result.add(rs.getString(1));

	    		    
	    
	    
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
	        if(rs != null) {            rs.close(); /* 선택 사항*/        }
	        if(stmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(pstmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(con != null) {con.close(); /* 필수 사항 */        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	return result;
	}
	public static ArrayList delete(String db_scanned,String Columnname) throws SQLException
	{Connection con = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	Statement stmt = null;   
	ResultSet rs = null;
	ArrayList result = new ArrayList();
	con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	pstmt = con.prepareStatement("select * from "+db_scanned);
    
	try {
	    Class.forName(db_start);
	    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	    stmt = con.createStatement();
	    rs = pstmt.executeQuery("select 테이블 from "+db_scanned+" WHERE 속성명 = '"+Columnname+"'");
	    pstmt.executeQuery("delete from "+db_scanned+" WHERE 속성명 = '"+Columnname+"'");
	    
	    int num=0;
	    while(rs.next())
	    {pstmt2 = con.prepareStatement("select * from "+rs.getString(1));
	    	pstmt2.executeQuery("alter table "+rs.getString(1)+" drop "+Columnname);

	    		    
	    
	    
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
	        if(rs != null) {            rs.close(); /* 선택 사항*/        }
	        if(stmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(pstmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(con != null) {con.close(); /* 필수 사항 */        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	return result;
	}
	public static ArrayList change(String db_scanned,String Columnname,String changetype) throws SQLException
	{Connection con = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	Statement stmt = null;   
	ResultSet rs = null;
	ArrayList result = new ArrayList();
	con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	pstmt = con.prepareStatement("select * from "+db_scanned);
    
	try {
	    Class.forName(db_start);
	    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
	    stmt = con.createStatement();
	    rs = pstmt.executeQuery("select 테이블명 from "+db_scanned+" WHERE 속성명 = '"+Columnname+"'");
	    pstmt.executeQuery("update set "+db_scanned+"");
	    
	    int num=0;
	    while(rs.next())
	    {pstmt2 = con.prepareStatement("select * from "+rs.getString(1));
	    	pstmt2.executeQuery("alter table "+rs.getString(1)+" drop "+Columnname);

	    		    
	    
	    
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
	        if(rs != null) {            rs.close(); /* 선택 사항*/        }
	        if(stmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(pstmt != null) {            stmt.close(); /* 선택사항이지만 호출 추천*/        }
	        if(con != null) {con.close(); /* 필수 사항 */        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	return result;
	}
	
	
	public static void mainscan(String db_scanned)throws SQLException{ 
	Connection con = null;
	PreparedStatement pstmt = null;   
	ResultSet rs = null;
	Statement st = null;


	try {
	
	    Class.forName(db_start);
	
	    con = DriverManager.getConnection(db_add,db_name,db_pswd
	        );
	               
	    pstmt = con.prepareStatement("select * from "+db_scanned);
	    ArrayList columnname = new ArrayList();
	    ArrayList columntype = new ArrayList();
	    ArrayList ListofList = new ArrayList();
	    rs = pstmt.executeQuery();
	
	    ResultSetMetaData rs2 = null;
	    rs2 = rs.getMetaData();
	    for(int n=0; n<rs2.getColumnCount(); n++) {
	    	ArrayList temp = new ArrayList();
	    	ListofList.add(temp);}
	    
    
	    //컬럼네임과 타입 리스트에 저장
	    for(int n=1; n<=rs2.getColumnCount(); n++)
	    {columnname.add(rs2.getColumnName(n));
	    		columntype.add(rs2.getColumnType(n));
	    //System.out.println(columnname.get(n-1));
	    }
	    ArrayList TEMP = null;
    	
    //값 뽑기
	    while(rs.next()) {for(int n=1; n<=rs2.getColumnCount(); n++) {
    	
	    	TEMP = (ArrayList) ListofList.get(n-1);
    	
	    	getcolumntypefunction(n,rs,columntype,TEMP);
    	
    	
    	/*
    	 * System.out.println(TEMP);
    	 */
    	}
    
    }
    //속성들 값 뽑은 리스트를 수치속성과 문자속성 리스트에 집어넣는다.
    ArrayList numericlist = new ArrayList();
    ArrayList Literallist = new ArrayList();
	    for(int n=0; n<ListofList.size(); n++) {
		    ArrayList TEMP3 = new ArrayList();
			TEMP3 = (ArrayList) ListofList.get(n);
			if((int)columntype.get(n) == 4) {
			 /*
			  System.out.println(scanlistmaker(TEMP3,(int) columntype.get(n),(String) columnname.get(n),db_scanned));
			  */
			  numericlist.add(scanlistmaker(TEMP3,(int) columntype.get(n),(String) columnname.get(n),db_scanned));
			}
			else if ((int)columntype.get(n) == 12) {
				/*
				 * System.out.println(scanlistmaker(TEMP3,(int) columntype.get(n),(String) columnname.get(n),db_scanned));
				 */
				Literallist.add(scanlistmaker(TEMP3,(int) columntype.get(n),(String) columnname.get(n),db_scanned));
			}
			
		}
    
	//for문으로 테이블에 인서트한다.
	
    for(int n=0; n<numericlist.size(); n++) 
    {ArrayList TEMPn = (ArrayList) numericlist.get(n);
    	String sql_c_info = "INSERT INTO c_info(테이블명, 속성명, 데이터타입, NULL레코드수, NULL레코드비율, 대표속성, 결합키후보, 대표결합키) VALUES (\'"
    						+TEMPn.get(12)+ "\', \'"+TEMPn.get(0)+"\', \'"+TEMPn.get(1)+"\', "+TEMPn.get(2)+", "+TEMPn.get(3)
    						+ ", " +TEMPn.get(9)+ ", \'"+TEMPn.get(10)+"\', "+TEMPn.get(11)+")";
    	String sql_c_numeric = "INSERT INTO c_numeric(테이블명, 속성명, 상이수치값,최대값,최소값,ZERO레코드수,ZERO레코드비율) VALUES (\'"
    			+ TEMPn.get(12)+ "\', \'"+TEMPn.get(0)+"\', " + TEMPn.get(4)+", "+TEMPn.get(5)+", "+TEMPn.get(6)+", "+TEMPn.get(7)+", "+TEMPn.get(8)+")";
    	
    	/*
    	 * System.out.println("\'"+TEMPn.get(0)+"\', \'"+TEMPn.get(1)+"\', "+TEMPn.get(2)+", "+TEMPn.get(3)+", "+TEMPn.get(4)+", "+TEMPn.get(5)+", "+TEMPn.get(6)+", "+TEMPn.get(7)+", "+TEMPn.get(8)+", "+TEMPn.get(9)+", \'"+TEMPn.get(10)+"\', "+TEMPn.get(11)+",\'"+TEMPn.get(12)+"\')");
    	 */
    	/*
    	st = con.createStatement();
    	st.executeUpdate(sql_c_info);
    	st.executeUpdate(sql_c_numeric);
    	*/
    }

    for(int n=0; n<Literallist.size(); n++) 
    {
    	
    	ArrayList TEMPl = (ArrayList) Literallist.get(n);
    	String sql_c_info = "INSERT INTO c_info(테이블명, 속성명, 데이터타입, NULL레코드수, NULL레코드비율, 대표속성, 결합키후보, 대표결합키) VALUES (\'"
				+TEMPl.get(10)+ "\', \'"+TEMPl.get(0)+"\', \'"+TEMPl.get(1)+"\', "+TEMPl.get(2)+", "+TEMPl.get(3)+", "+TEMPl.get(7)+", '"+TEMPl.get(8)+"', "+TEMPl.get(9) + ")"; //10, 0, 1, 2, 3, 7, 8, 9
    	String sql_c_category = "INSERT INTO c_category(테이블명, 속성명, 상이범주값, 특수문자포함레코드수, 특수문자포함레코드비율) VALUES (\'"
    			+TEMPl.get(10)+ "\', \'"+TEMPl.get(0)+"\', " + TEMPl.get(4)+", "+TEMPl.get(5)+", "+TEMPl.get(6)+")";
    	st = con.createStatement();
    	st.executeUpdate(sql_c_info);
    	st.executeUpdate(sql_c_category);
    }
    st = con.createStatement();
    String SQL = "UPDATE table_info SET done ='Y' WHERE name = \'" + db_scanned + "\'";
    st.executeUpdate(SQL);
    
    
} catch(Exception e) {
    e.printStackTrace();
} finally {
    try {
        if(rs != null) {
            rs.close(); // 선택 사항
        }
        
        if(pstmt != null) {
            pstmt.close(); // 선택사항이지만 호출 추천
        }
    
        if(con != null) {
            con.close(); // 필수 사항
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
	public static ArrayList dictionaryshow(String db_scanned) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList();
		try {
		    Class.forName(db_start);
		    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
		    pstmt = con.prepareStatement("select * from "+db_scanned);
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()) {
		    	result.add(rs.getString("skey"));
		    }
		}
		catch(Exception e) {
		    e.printStackTrace();
		} 
		finally {
		    try {
		        if(rs != null) {            rs.close(); /* 선택 사항*/        }
		        if(pstmt != null) {            pstmt.close(); /* 선택사항이지만 호출 추천*/        }
		        if(con != null) {con.close(); /* 필수 사항 */        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		return result;
	}
	public static ArrayList union_keyshow(String db_scanned) throws SQLException

		{Connection con = null;
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList();
		try {
		    Class.forName(db_start);
		    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
		    pstmt = con.prepareStatement("select * from "+db_scanned);
		    rs = pstmt.executeQuery();
		    
		    while(rs.next())
		    {result.add(rs.getString("union_key"));}
		}
		catch(Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if(rs != null) {            rs.close(); /* 선택 사항*/        }
		        if(pstmt != null) {            pstmt.close(); /* 선택사항이지만 호출 추천*/        }
		        if(con != null) {con.close(); /* 필수 사항 */        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		return result;
	}

	public static ArrayList dictionaryadd(String db_scanned,String add) throws SQLException
{Connection con = null;
PreparedStatement pstmt = null;   
int rs = 0;
ArrayList<String> result = new ArrayList();
try {
    Class.forName(db_start);
    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
    pstmt = con.prepareStatement("select * from "+db_scanned);
    rs = pstmt.executeUpdate("insert INTO dictionary(dictionary) VALUES('"+add+"')");
    
    
}
catch(Exception e) {
    e.printStackTrace();
} finally {
    try {
        if(pstmt != null) {            pstmt.close(); /* 선택사항이지만 호출 추천*/        }
        if(con != null) {con.close(); /* 필수 사항 */        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
return result;
}
	public static ArrayList union_keyadd(String db_scanned,String add) throws SQLException
{Connection con = null;
PreparedStatement pstmt = null;   
int rs = 0;
ArrayList<String> result = new ArrayList();
try {
    Class.forName(db_start);
    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
    pstmt = con.prepareStatement("select * from "+db_scanned);
    rs = pstmt.executeUpdate("insert INTO dictionary(dictionary) VALUES('"+add+"')");
    
    
}
catch(Exception e) {
    e.printStackTrace();
} finally {
    try {
        if(pstmt != null) {            pstmt.close(); /* 선택사항이지만 호출 추천*/        }
        if(con != null) {con.close(); /* 필수 사항 */        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
return result;
}



	public static ArrayList mapping(String skey, String cname, boolean is_dictionary) throws SQLException{
		Connection con = null;
		Statement st = null;   
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList();
		String columnname;
		if(is_dictionary) {columnname = "대표결합키";}
		else {columnname = "대표속성";}
		try {
		    Class.forName(db_start);
		    con = DriverManager.getConnection(db_add,db_name,db_pswd); 
		    st = con.createStatement();
		    rs = st.executeQuery("UPDATE c_info SET "+columnname+" = \'"+ skey + "\' WHERE 속성명 = \'" + cname + "\'");
		    
		
		}
		catch(Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		    	if(rs != null) { rs.close();}
		        if(st != null) {            st.close(); /* 선택사항이지만 호출 추천*/        }
		        if(con != null) {con.close(); /* 필수 사항 */        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		return result;
		}




    public static void main(String[] args) throws SQLException {    
    	String db_scanned = "3_physical_instructor_writing_info";
    
    
    tablescan();
    mainscan(db_scanned);
    
    /*
    System.out.println(dictionaryshow("standard_dictionary"));
    
    mapping("전화번호", "TEL_NUM",true);
    
    System.out.println(showtable(db_start,db_add,db_name,db_pswd));
    delete(db_start,db_add,db_name,db_pswd,"literals","INPUT_GBN");
    */
       
}}