package algorithm;

import entity.UserInfo;
import entity.infoField.BaseInfoFieldName;
import entity.infoField.CustomInfoFieldName;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IdenticalInfoFieldName;
import entity.infoField.InfoFieldName;

/**
 * ��IdenticalInfoFieldһ��
 * ���������ֶζ�һ��
 * @author Administrator
 *
 */
public class IdenticalUserMatcher implements Matcher {

	@Override
	public boolean match(Object pattern, Object target) {
		if (!(pattern instanceof UserInfo))
			return false;
		if (!(target instanceof UserInfo))
			return false;
		UserInfo pU = (UserInfo)pattern, tU = (UserInfo)target;
		for(IdenticalInfoFieldName name: IdenticalInfoFieldName.values()){
			IdenticalInfoField field1 = (IdenticalInfoField) pU.getInfoField(InfoFieldName.valueOf(name.name()));
			IdenticalInfoField field2 = (IdenticalInfoField) tU.getInfoField(InfoFieldName.valueOf(name.name()));
			if (!field1.isEmpty() && field1.toIDString().equals(field2.toIDString()))
				return true;
		}
		
		//���������ֶζ�һ��
		return pU.getStringValue().equals(tU.getStringValue());
	}

}
