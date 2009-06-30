package algorithm;

public class LCSTUserInfoMatcher extends UserInfoMatcher {
	@Override
	int similarity(String p, String t) {
		if (p.equals(t))//”≈ªØ
			return p.length();
		return StringFunc.lcst(p, t);
	}
	
	@Override
	int weight(String p, String t) {
		return p.length();
	}

	public LCSTUserInfoMatcher(double threshold){
		super(threshold);
	}
	
	public LCSTUserInfoMatcher() {
	}
}

