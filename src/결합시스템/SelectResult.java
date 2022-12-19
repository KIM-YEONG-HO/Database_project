package 결합시스템;

public interface SelectResult {

	public String showScannedTable();
	/*
	 속성 도메인 스캔이 완료된 테이블 목록을 추출할 수 있는 SQL문 전달
	 */
	public String showNumeric(String tname);
	/*
	 속성 도메인 스캔이 완료된 테이블 중 하나를 선택했을 때, 그 테이블의 수치 속성 테이블을 보여주는 SQL문 전달
	 */
	public String showCategory(String tname);
	/*
	 속성 도메인 스캔이 완료된 테이블 중 하나를 선택했을 때, 그 테이블의 범주 속성 테이블을 보여주는 SQL문 전달
	 */
	public void categorycsv(String address, String filename);
	/*
	 범주 속성 테이블을 csv로 받을 수 있게 해줌
	 */
	public void numericcsv(String address, String filename);
	/*
	 수치 속성 테이블을 csv로 받을 수 있게 해줌
	 */
	public String showSingleCombination();
	/*
	 단일 결합이 완료된 테이블의 결합 결과 목록을 보여주는 SQL문 전달
	 */
	public String showMulitCombination();
	/*
	 다중 결합이 완료된 테이블의 결합 결과 목록을 보여주는 SQL문 전달
	 */
	public void Joincsv(String address, String filename, String Jointname);
	/*
	 결합 테이블을 csv로 받을 수 있게 해줌
	 */
	public void singleResultcsv(String address, String filename);
	/*
	단일 결합 결과 목록을 csv로 받을 수 있게 해줌
	 */
	public void multiResultcsv(String address, String filename);
	/*
	다중 결합 결과 목록을 csv로 받을 수 있게 해줌
	 */
	
}
