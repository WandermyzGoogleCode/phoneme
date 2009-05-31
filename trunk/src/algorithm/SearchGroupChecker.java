package algorithm;

import entity.Group;

public class SearchGroupChecker implements Checker {

	@Override
	public boolean check(Object obj) {
		if (!(obj instanceof Group))
			return false;
		Group b = (Group)obj;
		//����ȫΪ��
		for(String key: b.getKeySet())
			if (!b.getInfoField(key).isEmpty())
				return true;
		return false;
	}

}
