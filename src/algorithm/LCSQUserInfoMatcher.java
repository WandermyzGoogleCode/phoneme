package algorithm;

public class LCSQUserInfoMatcher extends UserInfoMatcher {
	@Override
	int similarity(String p, String t) {
		return StringFunc.lcsq(p, t);
	}
	
	@Override
	int weight(String p, String t) {
		return p.length();
	}
	
	public LCSQUserInfoMatcher(double threshold){
		super(threshold);
	}
	
	public LCSQUserInfoMatcher() {
	}
}
