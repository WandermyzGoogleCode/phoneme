package algorithm;

import entity.BaseUserInfo;

public class SearchUserChecker implements Checker {

	@Override
	public boolean check(Object obj) {
		if (!(obj instanceof BaseUserInfo))
			return false;
		BaseUserInfo b = (BaseUserInfo)obj;
		//����ȫΪ��
		for(String key: b.getKeySet())
			if (!b.getInfoField(key).isEmpty())
				return true;
		return false;
	}

}
