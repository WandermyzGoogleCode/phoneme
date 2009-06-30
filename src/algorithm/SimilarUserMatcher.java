package algorithm;

import entity.UserInfo;
import entity.infoField.InfoFieldName;

/**
 * 根据某些字段，进行LCSQ（最长公子序列）模糊搜索
 * @author Administrator
 *
 */
public class SimilarUserMatcher implements Matcher {
	private static final double DefaultThreshold = 0.9;
	private LCSQUserInfoMatcher submatcher;

	public SimilarUserMatcher() {
		submatcher = new LCSQUserInfoMatcher(DefaultThreshold);
	}
	
	public SimilarUserMatcher(double threshold){
		submatcher = new LCSQUserInfoMatcher(threshold);
	}
	
	enum KeyInfoFieldName{
		Name, Phone, Birthday;
	}

	@Override
	public boolean match(Object pattern, Object target) {
		if (!(pattern instanceof UserInfo))
			return false;
		if (!(target instanceof UserInfo))
			return false;
		UserInfo pU = (UserInfo)pattern, tU = (UserInfo)target;
		UserInfo kpU = new UserInfo(), ktU = new UserInfo();
		for(KeyInfoFieldName key: KeyInfoFieldName.values())
			if (!tU.getInfoField(InfoFieldName.valueOf(key.name())).isEmpty()){
				kpU.setInfoField(pU.getInfoField(InfoFieldName.valueOf(key.name())));
				ktU.setInfoField(tU.getInfoField(InfoFieldName.valueOf(key.name())));
			}
		return submatcher.match(kpU, tU) && submatcher.match(ktU, pU);
	}

}
