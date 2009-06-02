package algorithm;

import java.sql.SQLException;

import serverLogicCenter.ServerLogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IdenticalInfoFieldName;
import entity.infoField.InfoField;

public class UserChecker implements Checker {
	private ServerLogicCenter center;
	
	public UserChecker(ServerLogicCenter center){
		this.center = center;
	}

	@Override
	public boolean check(Object obj) {
		if (!(obj instanceof BaseUserInfo))
			return false;
		BaseUserInfo b = (BaseUserInfo)obj;
		/*
		 * 名字不能为空，且至少有一个IdenticalInfoField不为空，
		 * 并且非空的IdenticalInfoField不能有冲突
		 */
		if (b.getName() == null || b.getName().equals(""))
			return false;
		int idCnt = 0;
		for(IdenticalInfoFieldName name: IdenticalInfoFieldName.values()){
			IdenticalInfoField field = (IdenticalInfoField)b.getInfoField(name.name());
			try {
				if (field != null && !field.isEmpty() && !center.getDataCenter().searchUserID(field).equals(b.getID()))
					return false;
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
				return false;
			}
			if (field != null && !field.isEmpty())
				idCnt++;
		}
		if (idCnt == 0)
			return false;
		return true;
	}

}
