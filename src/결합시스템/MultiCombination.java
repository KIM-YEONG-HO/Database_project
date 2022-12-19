package 결합시스템;

import java.util.ArrayList;
public class MultiCombination extends SingleCombination {
	ArrayList<String[]> Joinlist = new ArrayList<String[]>();
	public MultiCombination() {
		super();
	}
	public MultiCombination(String tname, String cname, String skey) {
		super(tname, cname, skey);
	}
	/*
	 showTable()
	 SearchTableName(), SearchColumnName(), SearchRColumn(), SearchSKey()
	 showSource()
	 FindSameTable()
	 
	 위 함수들은 SingleCombination Class 내 method를 사용
	 */
	
	public void addJoinList(String targettname, String skey) { 
		//결합할 테이블의 테이블명과 대표 결합키를 ArrayList에 [targettname, skey]형식으로 추가
		String[] t1 = {targettname, skey};
		Joinlist.add(t1);
	}
	
	public void MultiJoin() {
		//SingleCombinaiton의 insertJointable() 응용
		this.Flag = 1;
		for(int j=0; j<Joinlist.size(); j++) {
			String tt = Joinlist.get(j)[0];
			String tskey = Joinlist.get(j)[1];
			insertResultTable(tt, tskey);
		}
		for (int i=0; i<Joinlist.size(); i++) {
			this.findCname(Joinlist.get(i)[0],Joinlist.get(i)[1]);
			SingleJoin();
			updateResult();
		}
	}
}
