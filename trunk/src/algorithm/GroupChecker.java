package algorithm;

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
			Group oldG = center.getDataCenter().getGroup(g.getID());
			if (!g.getUserSet().equals(oldG.getUserSet()))
				return false;
		}
		return true;
	}

}
