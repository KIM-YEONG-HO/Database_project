package 결합시스템;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class SingleCombination implements Combination{
	String tname; // Source 테이블
	String skey; //  Source 테이블의 대표 결합키
	String cname; // Source 테이블의 대표 결합키가 매핑된 컬럼
	String targettname;
	String targetcname;
	Map<String, ArrayList<String>> CanSelect = new HashMap<String, ArrayList<String>>();
	DBcon con1 = new DBcon(); //DB접속
	
	public SingleCombination() {
		this.tname="";
		this.cname="";
		this.skey="";
	}
	
	public String showTable() {

		con1 = new DBcon();
		String result = "";
		String SQL = "CREATE OR REPLACE VIEW YouCanSelect AS " //View 생성
					+ "SELECT a.name AS \'테이블명\', a.num_record AS \'레코드수\', "
					+ "b.rcol AS \'대표속성\', b.skey AS \'대표결합키\' "
				   	+"FROM table_info AS a, c_info AS b "
				   	+"WHERE a.name = b.t_name AND " //두 테이블 조인한 후,
				   	+"a.done =\'Y\' AND " //속성 도메인 스캔이 완료된 테이블만
				   	+"b.skey != \'-\'"; //대표 결합키 결합이 완료된 테이블만
		try {
			con1.st=con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			con1.st=con1.con.createStatement();
			SQL = "SELECT * FROM YouCanSelect";
			con1.rs = con1.st.executeQuery(SQL);
			if (!con1.rs.next()) {
				result="선택 가능한 테이블이 없습니다";
			}
			else {
				result = "SELECT * FROM YouCanSelect";
			}
			con1.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println("선택가능한 테이블을 찾는 과정에서 에러가 발생했습니다.");
			System.exit(0);
		}
		return result;
	}

	public String SearchTableName(String tablename, String FromInnerQuery) {
		
		String SQL = "SELECT * FROM (" + FromInnerQuery+ ") as a WHERE a.테이블명 LIKE \'%" + tablename + "%\'";
		con1= new DBcon();
		try {
			con1.st=con1.con.createStatement();   
			con1.rs=con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				System.out.println("오류 : 선택 가능한 테이블 목록 중 하나를 선택하세요");
				SQL = "error";
			}
			
			con1.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println("입력한 테이블명을 선택 가능한 테이블 목록에서 검색하는 과정에서 에러가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		return SQL;
	}
	public String SearchColumnName(String cname, String FromInnerQuery) {
		String SQL = "SELECT DISTINCT t_name FROM c_info WHERE c_name LIKE \'%" + cname + "%\'";
		String result = "(";
		con1 = new DBcon();
		try {
			con1.st=con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			while (con1.rs.next()) {
				result=result + "\'" + con1.rs.getString(1) + "\'" + ",";
			}
			if (result.substring(result.length()-1).equals(",")){
				result = result.substring(0,result.length()-1);
			}
			result= result+")";
			con1.rs.close();
			con1.st.close();
			con1.con.close();
		}
		catch (SQLException s) {
			System.out.println("속성명을 검색하는 과정에서 에러가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		
		if (result.equals("()")) {
			System.out.println("오류 : 선택 가능한 테이블 목록 중 하나를 선택하세요");
			SQL = "error";
		}
		else {
			SQL = "SELECT * FROM (" + FromInnerQuery + ")as a WHERE a.테이블명 IN " + result;
		}
		
		con1 = new DBcon();
		try {
			con1.st=con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				SQL = "오류 : 선택 가능한 테이블 목록 중 하나를 선택하세요";
			}
			con1.rs.close();
			con1.st.close();
			con1.con.close();
		}
		catch (SQLException s1) {
			System.out.println("입력한 속성명을 지닌 테이블을 선택 가능한 테이블 목록에서 검색하는 과정에서 에러가 발생했습니다.");
			System.out.println(s1.getMessage());
			System.exit(0);
		}
		return SQL;
	}
	public String SearchRColumn(String rcol, String FromInnerQuery) {
		String SQL ="SELECT * FROM (" + FromInnerQuery + ")as a WHERE a.대표속성 = \'" + rcol +"\'";
		con1 = new DBcon();
		try {
			con1.st=con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				System.out.println("오류 : 선택 가능한 테이블 목록 중 하나를 선택하세요");
				SQL = "error";
			}
			con1.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println("입력받은 대표 속성 값을 선택 가능한 테이블 목록에서 검색하는 과정에서 에러가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		return SQL;
	}
	public String SearchSKey(String skey, String FromInnerQuery){
		String SQL = "SELECT * FROM (" + FromInnerQuery + ")as a WHERE a.대표결합키 = \'" + skey + "\'";
		con1 = new DBcon();
		try {
			con1.st=con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				System.out.println("오류 : 선택 가능한 테이블 목록 중 하나를 선택하세요");
				SQL = "error";
			}
			con1.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println("입력받은 대표 결합키를 가진 테이블을 선택 가능한 테이블 목록에서 검색하는 과정에서 에러가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		return SQL;
	}
	
	public String showSource(String tname, String skey) {
		String SQL = "SELECT c_name FROM c_info WHERE t_name = \'" + tname + "\' AND skey = \'" + skey + "\'";
		con1 = new DBcon();
		try {
			con1.st=con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			while(con1.rs.next()) {
				this.cname = con1.rs.getString(1);
			}
			con1.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println("선택한 테이블을 보여주는 과정에서 에러가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		this.tname = tname;
		this.skey = skey;
		SQL = "SELECT * FROM YouCanSelect WHERE 테이블명 = \'" + this.tname + "\'";
		return SQL;
		
	}
	
	public String FindSameTable() {
		con1 = new DBcon();
		String SQL = "SELECT * "
				   + "FROM YouCanSelect "
				   + "WHERE 대표결합키 = \'" + skey +"\' AND "
				   + "테이블명 != \'" + tname + "\'";
		try {
			con1.st=con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
	 		if (!con1.rs.next()) {
	 			System.out.println("대표 결합키가 일치하는 테이블이 없습니다. 다시 선택해주세요.");
	 			SQL = "error";
	 		}
	 		con1.DBdiscon();
		}
		catch (SQLException s) { 
			System.out.println("대표 결합키가 일치하는 테이블을 찾는 과정에서 에러가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		
		return SQL; 
	}
	
	public void SingleJoinTable(String targettname, String skey) { 
		this.targettname=targettname;
		con1 = new DBcon();
		String SQL = "SELECT c_name "
				+"FROM c_info "
				+"WHERE t_name = \'" + targettname + "\' AND "
				+"skey = \'" + skey + "\'";
		try {
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			while(con1.rs.next()) {
				this.targetcname = con1.rs.getString(1);
			}
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("Target 테이블의 속성명을 찾는 과정에서 오류가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		con1 = new DBcon();
		//결합결과 테이블에 넣을 value들 받아오기
		//table join하기
	 
	}
	public void downloadcsv(String address) {
		
	}
}

