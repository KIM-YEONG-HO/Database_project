package 결합시스템;
import java.sql.SQLException;

public class SingleCombination implements Combination{
	String tname; // Source 테이블
	String skey; //  Source 테이블의 대표 결합키
	String cname; // Source 테이블의 대표 결합키가 매핑된 컬럼
	String targettname;
	String targetcname;
	String jointname;
	int numRecordOfSource;
	int numRecordOfTarget;
	int numRecordOfJoin;
	int Flag = 0;
	boolean joinsuccess=false;
	DBcon con1 = new DBcon(); //DB접속
	
	public SingleCombination() {
		this.tname="";
		this.cname="";
		this.skey="";
	}
	public SingleCombination(String tname, String skey, String cname) {
		this.tname = tname;
		this.cname = cname;
		this.skey = skey;
	}
	
	public String showTable() {

		con1 = new DBcon();
		String result = "";
		String SQL = "CREATE OR REPLACE VIEW YouCanSelect AS " //View 생성
					+ "SELECT a.name AS \'테이블명\', a.num_record AS \'레코드수\', "
					+ "b.대표속성', b.대표결합키 "
				   	+"FROM table_info AS a, c_info AS b "
				   	+"WHERE a.name = b.테이블명 AND " //두 테이블 조인한 후,
				   	+"a.done =\'Y\' AND " //속성 도메인 스캔이 완료된 테이블만
				   	+"b.대표결합키 != \'-\'"; //대표 결합키 결합이 완료된 테이블만
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
		String SQL = "SELECT DISTINCT 테이블명 FROM c_info WHERE 속성명 LIKE \'%" + cname + "%\'";
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
		String SQL = "SELECT c_name FROM c_info WHERE 테이블명 = \'" + tname + "\' AND 대표결합키 = \'" + skey + "\'";
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
	
	public void findCname(String targettname, String skey) {
		//targettable의 skey가 mapping된 속성찾기
		this.targettname=targettname;
		con1 = new DBcon();
		String SQL = "SELECT c_name "
				+"FROM c_info "
				+"WHERE 테이블명 = \'" + targettname + "\' AND "
				+"대표결합키 = \'" + skey + "\'";
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
	}
	public boolean insertResultTable(String targettname, String skey) { 
		boolean complete=false;
		
		findCname(targettname, skey);
		
		//결합결과 테이블에 data넣기 
		numRecordOfSource = searchNumRecord(this.tname);
		numRecordOfTarget = searchNumRecord(this.targettname);
		this.jointname = this.tname+"_" + this.targettname + "_결합";
		con1 = new DBcon();
		String SQL = "INSERT INTO 결합결과 (테이블A, 테이블A레코드수, 결합키속성A, 테이블B, 테이블B레코드수, 결합키속성B, 대표결합키, 결합테이블명, Flag) " + 
			  "VALUES(\'" + this.tname +"\', " + numRecordOfSource + ", \'" + this.cname + "\', "
			 +"\'" + this.targettname +"\', " + numRecordOfTarget + ", \'" + this.targetcname + "\', " +
			  "\'"  + this.skey + "\', \'" + this.jointname + "\', " + this.Flag + ")";
		try {
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			complete=true;
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("결합결과를 불러오는 과정에서 오류가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		return complete;
		
	}
	public void SingleJoin() {
		//table join하기
	    joinsuccess =false;
		con1 = new DBcon();
		String SQL = "CREATE TABLE " + this.jointname + " AS("
					+"SELECT * FROM " + this.tname + " as source INNER JOIN "+ this.targettname +" AS target ON source." + this.cname + "=target." + this.targetcname
					+")";
		try {
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			joinsuccess = true;
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("결합을 진행하는 과정에서 오류가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
	}
	public boolean updateResult() {
		boolean updatedone = false;
		if(joinsuccess = true) {
			con1 = new DBcon();
			String SQL = "SELECT count(*) FROM " + this.jointname;
			try {
				con1.st = con1.con.createStatement();
				con1.rs = con1.st.executeQuery(SQL);
				if(con1.rs.next()){
					numRecordOfJoin= con1.rs.getInt(1);
				}
				con1.DBdiscon();
			}
			catch (SQLException s){
				System.out.println("결합 테이블의 레코드 수를 가져오는 과정에서 오류가 발생했습니다.");
				System.out.println(s.getMessage());
				System.exit(0);
			}
		}
		else {
			System.out.println("결합이 제대로 완료되지 않아 결합결과를 UPDATE할 수 없습니다.");
			System.exit(0);
		}
		double w1 = (double)numRecordOfJoin /(double)numRecordOfSource;
		double w2 = (double)numRecordOfJoin /(double) numRecordOfTarget;
		System.out.println(numRecordOfJoin +  " " + w1 + " " + w2);
		con1 = new DBcon();
		
		try{
			con1.st = con1.con.createStatement();
			String SQL = "UPDATE 결합결과 SET 결과레코드수 = "+ this.numRecordOfJoin 
					+" WHERE 테이블A = \'"+ this.tname + "\' AND 테이블B = \'" + this.targettname + "\' AND 대표결합키 = \'" + this.skey + "\'";
			con1.rs = con1.st.executeQuery(SQL);
			SQL = "UPDATE 결합결과 SET 결합성공률W1 = " + w1
					+" WHERE 테이블A = \'"+ this.tname + "\' AND 테이블B = \'" + this.targettname + "\' AND 대표결합키 = \'" + this.skey + "\'";
			con1.rs = con1.st.executeQuery(SQL);
			SQL = "UPDATE 결합결과 SET 결합성공률W2 = " + w2
					+" WHERE 테이블A = \'"+ this.tname + "\' AND 테이블B = \'" + this.targettname + "\' AND 대표결합키 = \'" + this.skey + "\'";
			con1.rs = con1.st.executeQuery(SQL);
			SQL = "UPDATE 결합결과 SET 결합진행상황 = \'완료\' "
					+" WHERE 테이블A = \'"+ this.tname + "\' AND 테이블B = \'" + this.targettname + "\' AND 대표결합키 = \'" + this.skey + "\'";
			con1.rs = con1.st.executeQuery(SQL);
			updatedone=true;
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("결합결과를 UPDATE하는 과정에서 오류가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		return updatedone;

	}
	public String showResult() {
		String SQL = "SELECT 테이블A, 테이블A레코드수, 결합키속성A, 테이블B, 테이블B레코드수, 결합키속성B, 대표결합키, 결과레코드수, 결합성공률W1, 결합성공률W2, 결합진행상황, 결합테이블명 "
				+ "FROM 결합결과 WHERE 테이블A = \'"+ tname + "\' AND 테이블B = \'" + targettname + "\' AND 대표결합키 = \'" +skey + "\' AND Flag = 0" ;
		return SQL;
	}

	public void downloadcsv(String address, String filename) {
		String SQL = "SELECT * FROM " + this.jointname + " INTO OUTFILE \'" + address + "/" + filename + ".csv\' "
				   + "FIELDS TERMINATED BY \',\' "
				   + "LINES TERMINATED BY \'\\n\'";
		con1 = new DBcon();
		try{
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("결합결과를 csv 파일로 내려받는 과정 중 오류가 발생했습니다");
			System.out.println(s.getMessage());
			System.exit(0);
		}
	}
	public int searchNumRecord(String tablename) {
		con1 = new DBcon();
		int returnvalue = -1; //결과값 없을 시 -1
		String SQLtemp = "Select num_record from table_info where name = \'" + tablename + "\'";
		try {
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQLtemp);
			while(con1.rs.next()) {
				 returnvalue= con1.rs.getInt(1);
			}
			con1.DBdiscon();
			
		}
		catch (SQLException s){
			System.out.println(tablename + " 테이블의 레코드 수를 가져오는 과정에서 오류가 발생했습니다.");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		return returnvalue;
	}
	//입력된 table의 레코드 수 가져오기 
}

