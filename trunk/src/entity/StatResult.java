package entity;

import java.util.List;

/**
 * ͳ�����������յķֲ���
 * �Ժ��������
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
	 * 12���£�ÿ�������й����յ���
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
