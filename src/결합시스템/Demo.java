package 결합시스템;

import java.util.Scanner;
import java.sql.SQLException;

public class Demo {
	static SingleCombination demo = new SingleCombination();
	static MultiCombination demo1 = new MultiCombination();
	static SelectResultTable demo2 = new SelectResultTable();
	static DBcon democon = new DBcon();
	static Scanner sc = new Scanner(System.in);
	static String result = "";
	public static void main(String[] args){
		/*
		System.out.println("-----------------선택가능 테이블 목록----------------");
		System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
		System.out.println("--------------------------------------------------");
		result = demo1.showTable();
		try {
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			String temp = "";
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.println(temp);
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println("SQL 실행 과정에서 에러가 발생했습니다.");
			System.exit(0);
		}
		*/
		
		/*
		단일 결합/다중결합 test
		
		demoSearchColumnName(); 테스트 완료
		demoSearchSKey(); 테스트 완료
		*/
		
		/*
		demoshowSource(); 테스트 완료
		demoFindSameTable(); 테스트 완료
		
		System.out.println(demo.insertResultTable("건강정보", "전화번호")); 테스트 완료
		demoshowResult(); 테스트 완료
		demo.SingleJoin(); 테스트 완료
		System.out.println(demo.updateResult()); 테스트 완료
		demoshowResult(); 테스트 완료
		-> 동적으로 돌리기위해선??
		
		demo.downloadcsv("/Users/a05/Desktop", "결합저장이요"); 테스트 완료
		
		
		demoshowSource();
		demoFindSameTable();
		
		demo1.addJoinList("건강정보", "전화번호");
		demo1.MultiJoin();
		
		
		demoshowScannedTable();
		
		System.out.println(demo2.showCategory("건강정보")); 
		System.out.println(demo2.showNumeric("건강정보"));
		
		
		demo2.multiResultcsv("/Users/a05/Desktop", "다중결합결과예시");
		*/
		result = demo2.minCombRecord(7);
		try {
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			String temp = "";
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(5);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(6);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(7);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(8);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(9);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(10);
				System.out.printf(temp + "          ");
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println("SQL 실행 과정에서 에러가 발생했습니다.");
			System.exit(0);
		}
		
	}


		
	public static void demoSearchTableName() {
		System.out.println("");
		System.out.println("");
		System.out.println("테이블명 검색 : ");
		String tn = sc.next();
		result = demo.SearchTableName(tn, result);
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("-----------------선택가능 테이블 목록----------------");
			System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
			System.out.println("--------------------------------------------------");
			String temp;
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.println(temp);
			}
			democon.rs.close();
			democon.st.close();
			democon.con.close();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}
	public static void demoSearchColumnName() {
		System.out.println("");
		System.out.println("");
		System.out.println("속성명 검색 : ");
		String cn = sc.next();
		result = demo.SearchColumnName(cn, result);
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("-----------------선택가능 테이블 목록----------------");
			System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
			System.out.println("--------------------------------------------------");
			String temp;
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.println(temp);
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}
	public static void demoSearchRColumnName() {
		System.out.println("");
		System.out.println("");
		System.out.println("대표 속성 검색 : ");
		String rc = sc.next();
		result = demo.SearchRColumn(rc, result);
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("-----------------선택가능 테이블 목록----------------");
			System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
			System.out.println("--------------------------------------------------");
			String temp;
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.println(temp);
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}
	public static void demoSearchSKey() {
		System.out.println("");
		System.out.println("");
		System.out.println("대표 속성 검색 : ");
		String sk = sc.next();
		result = demo.SearchSKey(sk, result);
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("-----------------선택가능 테이블 목록----------------");
			System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
			System.out.println("--------------------------------------------------");
			String temp;
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.println(temp);
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}
	public static void demoshowSource() {
		System.out.println("");
		System.out.println("");
		System.out.println("테이블명 : ");
		String tn = sc.next();
		System.out.println("대표결합키 : ");
		String sk = sc.next();
		result = demo1.showSource(tn, sk);
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("-----------------선택가능 테이블 목록----------------");
			System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
			System.out.println("--------------------------------------------------");
			String temp;
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.println(temp);
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}
	public static void demoshowScannedTable() {
		System.out.println("");
		System.out.println("");
		result = demo2.showScannedTable();
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("-----------------선택가능 테이블 목록----------------");
			System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
			System.out.println("--------------------------------------------------");
			String temp;
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}
	public static void demoFindSameTable() {
		System.out.println("");
		System.out.println("");
		result = demo1.FindSameTable();
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("----------Source 테이블과 결합 가능한 테이블 목록----------");
			System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
			System.out.println("--------------------------------------------------");
			String temp;
			while (democon.rs.next()) {
				temp=democon.rs.getString(1);
				System.out.printf(temp + "        ");
				temp=democon.rs.getString(2);
				System.out.printf(temp + "            ");
				temp=democon.rs.getString(3);
				System.out.printf(temp + "          ");
				temp=democon.rs.getString(4);
				System.out.println(temp);
				System.out.println("");
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}
	public static void demoshowResult() {
		System.out.println("");
		System.out.println("");
		result = demo1.showResult();
		try {
			democon = new DBcon();
			democon.st=democon.con.createStatement();
			democon.rs = democon.st.executeQuery(result);
			System.out.println("----------결합  결과  예시----------");
			String temp="";
			while (democon.rs.next()) {
				for(int i=1; i<12; i++) {
					temp=democon.rs.getString(i);
					System.out.printf(temp + "        ");
				}
				System.out.println(democon.rs.getString(12));
				System.out.println("");
			}
			democon.DBdiscon();
		}
		catch (SQLException s) {
			System.out.println(s.getMessage());
		}
	}

}
