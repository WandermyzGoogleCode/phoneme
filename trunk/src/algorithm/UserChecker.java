package algorithm;

import java.sql.SQLException;

import serverLogicCenter.sdataCenter.ServerDataCenter;
import entity.BaseUserInfo;
import entity.ID;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IdenticalInfoFieldName;

public class UserChecker implements Checker {
	private ServerDataCenter center;
	
	public UserChecker(ServerDataCenter center){
		this.center = center;
	}

	@Override
	public boolean check(Object obj) {
		if (!(obj instanceof BaseUserInfo))
			return false;
		BaseUserInfo b = (BaseUserInfo)obj;
		/*
		 * ���ֲ���Ϊ�գ���������һ��IdenticalInfoField��Ϊ�գ�
		 * ���ҷǿյ�IdenticalInfoField�����г�ͻ
		 */
		if (b.getName() == null || b.getName().equals(""))
			return false;
		int idCnt = 0;
		for(IdenticalInfoFieldName name: IdenticalInfoFieldName.values()){
			IdenticalInfoField field = (IdenticalInfoField)b.getInfoField(name.name());
			try {
				if (field != null && !field.isEmpty()){
					ID oid = center.searchUserID(field);
					if (oid != null && !oid.isNull() && !oid.equals(b.getID()))
						return false;
				}
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
