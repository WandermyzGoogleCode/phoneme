package algorithm;

import java.util.Iterator;

import entity.UserInfo;
import entity.infoField.InfoField;

public abstract class UserInfoMatcher implements Matcher {
	private double threshold = 0.5;

	abstract int similarity(String a, String b);
	abstract int weight(String a, String b);
	
	@Override
	public boolean match(Object pattern, Object target) {
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
				totalCnt += weight(p, t);
				matchCnt += similarity(p, t);
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
				totalCnt += weight(p, t);
				matchCnt += similarity(p, t);
			}
		}
		totalCnt = Math.max(totalCnt, 1);//防止除以0
		double matchRate=(double)matchCnt/totalCnt;
		if(matchRate>=threshold)
			return true;
		else
			return false;		
	}
	
	public UserInfoMatcher(){}
	
	public UserInfoMatcher(double threshold){
		this.threshold = threshold;
	}
}
