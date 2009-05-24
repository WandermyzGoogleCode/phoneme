package algorithm;

import java.util.Iterator;

import entity.UserInfo;
import entity.infoField.InfoField;

public class SimpleUserInfoMatcherUsingLCST implements Matcher{
	/**
	 * 传入的两个Object都是UserInfo
	 * 注意，pattern中可能有很多字段为空。
	 */
	@Override
	public boolean match(Object pattern, Object target) {
		//匹配两个UserInfo
		if((pattern instanceof UserInfo)||(target instanceof UserInfo)){
			if(!(pattern instanceof UserInfo)||!(target instanceof UserInfo))
				return false;
			else{
				UserInfo patInfo=(UserInfo)pattern;
				UserInfo tarInfo=(UserInfo)target;
				//若ID相同则直接返回真
				if(patInfo.getBaseInfo().getID().getValue()==tarInfo.getBaseInfo().getID().getValue())
					return true;
				//ID不同逐个匹配字段
				int totalCnt=0;
				int matchCnt=0;
				Iterator<String> iter=patInfo.getBaseInfo().getKeySet().iterator();
				while(iter.hasNext()){
					String field=iter.next();
					InfoField infoFiled = patInfo.getBaseInfo().getInfoField(field);
					if(infoFiled !=null && infoFiled.isValid()){
						String p = patInfo.getBaseInfo().getInfoField(field).getStringValue();
						String t = tarInfo.getBaseInfo().getInfoField(field).getStringValue();
						p = p.toLowerCase();
						t = t.toLowerCase();
						totalCnt += p.length();
						matchCnt += StringFunc.lcst(p, t);
					}
				}
				iter=patInfo.getCustomInfo().getKeySet().iterator();
				while(iter.hasNext()){
					String field=iter.next();
					InfoField infoFiled = patInfo.getCustomInfo().getInfoField(field);
					if(infoFiled !=null && infoFiled.isValid()){
						String p = patInfo.getCustomInfo().getInfoField(field).getStringValue();
						String t = tarInfo.getCustomInfo().getInfoField(field).getStringValue();
						p = p.toLowerCase();
						t = t.toLowerCase();
						totalCnt += p.length();
						matchCnt += StringFunc.lcst(p, t);
					}
				}
				totalCnt = Math.max(totalCnt, 1);
				double matchRate=(double)matchCnt/totalCnt;
				if(matchRate>=0.5)
					return true;
				else
					return false;
			}
		}
		//TODO 其他Object匹配
		return false;
	}
}
