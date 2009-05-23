package algorithm; 

import java.util.Iterator;

import entity.UserInfo;
import entity.BaseUserInfo;
import entity.CustomUserInfo;

public class SimpleUserInfoMatcher implements Matcher {

	/**
	 * ���������Object����UserInfo
	 * ע�⣬pattern�п����кܶ��ֶ�Ϊ�ա�
	 */
	@Override
	public boolean match(Object pattern, Object target) {
		//ƥ������UserInfo
		if((pattern instanceof UserInfo)||(target instanceof UserInfo)){
			if(!(pattern instanceof UserInfo)||!(target instanceof UserInfo))
				return false;
			else{
				UserInfo patInfo=(UserInfo)pattern;
				UserInfo tarInfo=(UserInfo)target;
				//��ID��ͬ��ֱ�ӷ�����
				if(patInfo.getBaseInfo().getID().getValue()==tarInfo.getBaseInfo().getID().getValue())
					return true;
				//ID��ͬ���ƥ���ֶ�
				int totalField=0;
				int matchField=0;
				Iterator<String> iter=patInfo.getBaseInfo().getKeySet().iterator();
				while(iter.hasNext()){
					String field=iter.next();
					if(patInfo.getBaseInfo().getInfoField(field)!=null){
						totalField++;
						if(patInfo.getBaseInfo().getInfoField(field).equals(tarInfo.getBaseInfo().getInfoField(field)))
							matchField++;
					}
				}
				iter=patInfo.getCustomInfo().getKeySet().iterator();
				while(iter.hasNext()){
					String field=iter.next();
					if(patInfo.getCustomInfo().getInfoField(field)!=null){
						totalField++;
						if(patInfo.getCustomInfo().getInfoField(field).equals(tarInfo.getBaseInfo().getInfoField(field)))
							matchField++;
					}
				}
				double matchRate=(double)(matchField/totalField);
				if(matchRate>=0.5)
					return true;
				else
					return false;
			}
		}
		//TODO ����Objectƥ��
		return false;
	}

}
