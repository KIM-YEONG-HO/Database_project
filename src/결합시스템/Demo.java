package 결합시스템;

import java.util.Scanner;
import java.sql.SQLException;

public class Demo {
	static SingleCombination demo = new SingleCombination();
	static DBcon democon = new DBcon();
	static Scanner sc = new Scanner(System.in);
	static String result = "";
	public static void main(String[] args){
		
		
		System.out.println("-----------------선택가능 테이블 목록----------------");
		System.out.println("테이블명  |     레코드 수   |  대표속성  |   대표 결합키     ");
		System.out.println("--------------------------------------------------");
		result = demo.showTable();
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
		/*
		demoSearchColumnName();
		demoSearchSKey();
		*/
		demoshowSource();
		demoFindSameTable();		
		
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
		result = demo.showSource(tn, sk);
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
	public static void demoFindSameTable() {
		System.out.println("");
		System.out.println("");
		result = demo.FindSameTable();
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
}
