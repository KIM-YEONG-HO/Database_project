package 결합시스템;

import java.sql.SQLException;

public class SelectResultTable implements SelectResult{
	DBcon con1 = new DBcon(); 
	String tname;

	public String showScannedTable() {
		String SQL = "SELECT name AS 테이블명, num_record AS 레코드수 FROM table_info WHERE done = \'Y\'";
		return SQL;
	}


	public String showNumeric(String tablename) {
		String SQL = "SELECT ci.속성명, ci.데이터타입, ci.NULL레코드수, ci.NULL레코드비율, cn.상이수치값, cn.최대값, cn.최소값, cn.ZERO레코드수, cn.ZERO레코드비율, ci.대표속성, ci.결합키후보, ci.대표결합키"
					+"FROM c_info as ci, c_numeric as cn"
					+"WHERE ci.속성명 = cn.속성명 AND cn.테이블명 = \'" + tablename + "\'";
		return SQL;
	}

	@Override
	public String showCategory(String tablename) {
		String SQL = "SELECT ci.속성명, ci.데이터타입, ci.NULL레코드수, ci.NULL레코드비율, cc.상이범주값, cc.특수문자포함레코드수, cc.특수문자포함레코드비율, ci.대표속성, ci.결합키후보, ci.대표결합키"
					+"FROM c_info as ci, c_category as cc"
					+"WHERE ci.속성명 = cc.속성명 AND cc.테이블명 = \'" + tablename + "\'";
		return SQL;
	}

	public void categorycsv(String address, String filename) {
		String SQL =  "SELECT ci.속성명, ci.데이터타입, ci.NULL레코드수, ci.NULL레코드비율, cc.상이범주값, cc.특수문자포함레코드수, cc.특수문자포함레코드비율, ci.대표속성, ci.결합키후보, ci.대표결합키"
				   + "FROM c_info ci, c_category cc "
				   + "WHERE ci.속성명 = cc.속성명 AND ci.테이블명 = \'" + this.tname + "\'"
				   +" INTO OUTFILE \'" + address + "/" + filename + ".csv\' "
				   + "FIELDS TERMINATED BY \',\' "
				   + "LINES TERMINATED BY \'\\n\'";
		con1 = new DBcon();
		try{
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("범주 속성 스캔 파일을 csv 파일로 내려받는 과정 중 오류가 발생했습니다");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		
	}

	@Override
	public void numericcsv(String address, String filename) {
		String SQL= "SELECT ci.속성명, ci.데이터타입, ci.NULL레코드수, ci.NULL레코드비율, cn.상이수치값, cn.최대값, cn.최소값, cn.ZERO레코드수, cn.ZERO레코드비율, ci.대표속성, ci.결합키후보, ci.대표결합키"
					+"FROM c_info as ci, c_numeric as cn"
					+"WHERE ci.속성명 = cn.속성명 AND cn.테이블명 = \'" + this.tname + "\'"
					+" INTO OUTFILE \'" + address + "/" + filename + ".csv\' "
					+ "FIELDS TERMINATED BY \',\' "
					+ "LINES TERMINATED BY \'\\n\'";
		con1 = new DBcon();
		try{
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("수치 속성 스캔 파일을 csv 파일로 내려받는 과정 중 오류가 발생했습니다");
			System.out.println(s.getMessage());
			System.exit(0);
		}
		
	}

	@Override
	public String showSingleCombination() {
		String SQL = "SELECT 테이블A, 테이블A레코드수, 결합키속성A, 테이블B, 테이블B레코드수, 결합키속성B, 대표결합키, 결과레코드수, 결합성공률W1, 결합성공률W2, 결합진행상황, 결합테이블명 "
				   + "FROM 결합결과 WHERE Flag = 0" ;
		return SQL;
	}

	@Override
	public String showMulitCombination() {
		String SQL = "SELECT 테이블A, 테이블A레코드수, 결합키속성A, 테이블B, 테이블B레코드수, 결합키속성B, 대표결합키, 결과레코드수, 결합성공률W1, 결합성공률W2, 결합진행상황, 결합테이블명 "
				   + "FROM 결합결과 WHERE Flag = 1" ;
		return SQL;
	}

	@Override
	public void Joincsv(String address, String filename, String jointname) {
		String SQL = "SELECT * FROM " + jointname + " INTO OUTFILE \'" + address + "/" + filename + ".csv\' "
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

	@Override
	public void singleResultcsv(String address, String filename) {
		String SQL = "SELECT 테이블A, 테이블A레코드수, 결합키속성A, 테이블B, 테이블B레코드수, 결합키속성B, 대표결합키, 결과레코드수, 결합성공률W1, 결합성공률W2, 결합진행상황, 결합테이블명 "
				   + "FROM 결합결과 WHERE Flag=0 "
				   + "INTO OUTFILE \'" + address + "/" + filename + ".csv\' "
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

	@Override
	public void multiResultcsv(String address, String filename) {
		String SQL = "SELECT 테이블A, 테이블A레코드수, 결합키속성A, 테이블B, 테이블B레코드수, 결합키속성B, 대표결합키, 결과레코드수, 결합성공률W1, 결합성공률W2, 결합진행상황, 결합테이블명 "
				   + "FROM 결합결과 WHERE Flag=1 "
				   + "INTO OUTFILE \'" + address + "/" + filename + ".csv\' "
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
	public String joinedWith(String tname) { //입력받은 테이블명과 결합한 테이블 결합결과를 모두 표시하는 SQL문 추출
		String SQL = "SELECT * FROM 결합결과 WHERE 테이블A = \'" + tname + "\' OR 테이블B = \'" + tname + "\'";
		con1 = new DBcon();
		try{
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				throw new SQLException();
			}
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("조건에 맞는 결합결과가 존재하지 않습니다.");
			System.exit(0);
		}
		return SQL;
	}
	public String threshold(double number) { //결합성공률이 입력받은 임계값(number) 이상인 결합성공률을 지닌 결합결과만 표시하는 SQL문 추출
		String SQL = "SELECT * FROM 결합결과 WHERE 결합성공률W1 >= " + number + "OR 결합성공률W2 >= " + number;
		con1 = new DBcon();
		try{
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				throw new SQLException();
			}
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("조건에 맞는 결합결과가 존재하지 않습니다.");
			System.exit(0);
		}
		return SQL;
	}
	public String tnameAndThreshod(String tname, double number) { //입력받은 테이블과 결합한 테이블 중 임계값 이상인 결합성공률을 지닌 결합결과만 표시하는 SQL문 추출
		String SQL = "SELECT * FROM ("+ joinedWith(tname) + ") as temp WHERE temp.결합성공률W1 >= " + number + " OR temp.결합성공률W2 >= " + number;
		con1 = new DBcon();
		try{
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				throw new SQLException();
			}
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("조건에 맞는 결합결과가 존재하지 않습니다.");
			System.exit(0);
		}
		return SQL;
	}
	public String minCombRecord(int minrecord) { //입력받은 최소 레코드 수보다 더 큰 결과레코드 수를 지닌 결합결과만 추출
		String SQL = "SELECT * FROM 결합결과 WHERE 결과레코드수 > " + minrecord;
		con1 = new DBcon();
		try{
			con1.st = con1.con.createStatement();
			con1.rs = con1.st.executeQuery(SQL);
			if(!con1.rs.next()) {
				throw new SQLException();
			}
			con1.DBdiscon();
		}
		catch (SQLException s){
			System.out.println("조건에 맞는 결합결과가 존재하지 않습니다.");
			System.exit(0);
		}
		return SQL;
	}

}
