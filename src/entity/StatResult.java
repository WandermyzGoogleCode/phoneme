package entity;

import java.util.List;

/**
 * 统计人数、生日的分布等
 * 以后待定……
 * @author Administrator
 *
 */
public class StatResult {
	private int totalCnt;
	private List<UserInfo> birthDistrib[];
	
	public int getTotalCnt(){
		return totalCnt;
	}
	
	/**
	 * 12个月，每个月所有过生日的人
	 * @return
	 */
	public List<UserInfo>[] getBirthDistrib(){
		return birthDistrib;
	}
	
	public void setTotalCnt(int cnt){
		totalCnt = cnt;
	}
	
	public void setBirthDistrib(List<UserInfo> distrib[]){
		birthDistrib = distrib;
	}
}
