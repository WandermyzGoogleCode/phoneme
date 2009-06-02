package algorithm;

import java.sql.SQLException;

import serverLogicCenter.ServerLogicCenter;
import entity.Group;

public class GroupChecker implements Checker {
	private ServerLogicCenter center;
	
	public GroupChecker(ServerLogicCenter center){
		this.center = center;
	}

	@Override
	public boolean check(Object obj) {
		if (!(obj instanceof Group))
			return false;
		Group g = (Group)obj;
		//���ֲ���Ϊ��
		if (g.getName() == null || g.getName().equals(""))
			return false;
		/*
		 * ���ID��Ϊ�գ���ô���Ǳ༭Ⱥ�飬��ʱ���ܶ��ڲ��ĳ�Ա
		 * ����������
		 */
		if (g.getID() != null && !g.getID().isNull()){
			Group oldG;
			try {
				oldG = center.getDataCenter().getGroup(g.getID());
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
				return false;
			}
			if (!g.getUserSet().equals(oldG.getUserSet()))
				return false;
		}
		return true;
	}

}
