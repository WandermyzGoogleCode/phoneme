package algorithm;

public class LCSTUserInfoMatcher extends UserInfoMatcher {
	@Override
	int similarity(String p, String t) {
		return StringFunc.lcst(p, t);
	}
	
	@Override
	int weight(String p, String t) {
		return p.length();
	}
}

