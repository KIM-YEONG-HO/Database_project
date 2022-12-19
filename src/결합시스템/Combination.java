package 결합시스템;


public interface Combination {
	public String showTable();
	/*
	 NOTE : 속성 도메인 스캔 완료 후 대표 결합키 설정이 완료된 테이블 정보 전달
	 CDOE : 속성도메인 스캔이 완료된 (테이블명, 레코드 수, 대표속성, 대표 결합키)를 따로 view로 저장
	 		
	 FRONT에서 전달받은 값 : X
	 RETURN 
	 존재할 시 : SELECT * FROM View
	 존재하지 않을 시 : 문자열 "선택 가능한 테이블이 없습니다" 전달
	 */
	

	public String SearchTableName(String tname, String FromInnerQuery);
	public String SearchColumnName(String cname, String FromInnerQuery);
	public String SearchRColumn(String rcol, String FromInnerQuery);
	public String SearchSKey(String skey, String FromInnerQuery);
	/*
	 NOTE : showTable()로 추출된 테이블 목록 중에서 테이블명, 대표 결합키, 대표 속성, 속성명을 검색하여
	 		이에 맞는 table을 보여주는 Function
	 CODE : showTable()결과로 저장된 view에 값이 존재하는지 확인 
	 		검색받은 조건에 알맞는 함수 차례대로 투입 
	 		첫 검색 함수의 FromInnerQuery값은 showTable() return 값
	 		두번째부터 검색 함수의 FromInnerQuery값은 이전 검색 함수의 return 값
	 
	 FRONT에서 전달받은 값 : 테이블명(전체 or 일부), 대표결합키, 대표 속성, 속성명(전체 or 일부)
	 RETURN
	 존재할 시 : showTable()를 통해 만들어진 view에서 조건에 맞는 테이블을 추출할 수 있는 SQL문 전달
	 존재하지 않을 시 : 문자열 "error" 전달
	*/
	public String showSource(String tname, String skey);
	/* 
	 NOTE : Search를 통해 찾은 테이블 중 하나를 선택했을 때 보여주는 테이블 정보 
	 CODE : showTable()을 통해 만들어낸 view에서 SQL문으로 선택한 source table 추출
	 
	 FRONT에서 전달받은 값 : 테이블명, 대표 결합키
	 RETURN : 조건에 맞는 테이블을 추출할 수 있는 SQL문 전달
	 */
	
	public String FindSameTable();  
	/*
	 NOTE : SourceTable과 대표 결합키가 같은 테이블을 찾음
	 CODE : view에서 SourceTable과 같은 대표결합키를 가지는 테이블을 찾는 SQL문 추
	 FRONT에서 전달받은 값 : X but 반드시 showSource() 이후에 전달
	 RETURN 
	 존재할 시 :조건에 맞는 테이블 정보를 추출할 수 있는 SQL문 전달
	 존재하지 않을 시 : 문자열 "error" 전달
	 */
	public boolean insertResultTable(String targetname, String skey);
	/*
	 NOTE : FindSameTable()을 통해 보여준 테이블 목록에서 결합 버튼을 클릭한 테이블을 showSource를 통해
	 		보여준 테이블과 결합할 때 결합 결과 예시를 보여줌
	 CDOE : 1) target table의 동일한 skey를 가진 column name을 sql문을 통해 찾는다
	 		2) 결합결과 테이블에 적절한 값을 집어넣는다
	 	
	 FRONT에서 전달받은 값 : 선택한 target 테이블명, 대표 결합키
	 */
	public void SingleJoin();
	/*
	 NOTE : insertResultTable후 실제 db내에서 결합을 진행한다.
	 CDOE : target table의 table name, column name을 이용하여 Source table과 Join한다.
	 */
	public boolean updateResult();
	/*
	 NOTE : SingleJoin이 완료됐다면 결합결과 테이블의 내용을 update한다.
	 CDOE : join이 완료되면 결합결과 table의 결과 레코드수, W1, W2, 결합 진행상황 값 변경
	 */
	public String showResult();
	/*
	 NOTE : 결합결과 table에서 source테이블명, target테이블명, 대표결합키를 Key로 가지는 record를 보여준다.
	 RETURN : 결합결과 table에서 주어진 변수들을 key로 가지는 record 추출 SQL문 전달
	 */
	public void downloadcsv(String address, String filename);
	/*
	 NOTE : SingleJoinTable이 완료된 후 실행되며 csv 다운로드 버튼을 누를 시 상대방이 지정한 위치로 join 결과를 다운로드 할 수 있게 한다.
	 CDOE : 1) 입력받은 address과 filename을 이용해 join결과를 csv export 		 		
	 */	
	
}
