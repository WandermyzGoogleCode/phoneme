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
		//名字不能为空
		if (g.getName() == null || g.getName().equals(""))
			return false;
		/*
		 * 如果ID不为空，那么就是编辑群组，此时不能对内部的成员
		 * 进行增减。
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
