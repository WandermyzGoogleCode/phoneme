package algorithm; 

public class SimpleUserInfoMatcher extends UserInfoMatcher {
	@Override
	int similarity(String a, String b) {
		return a.equals(b) ? 1 : 0;
	}
	
	@Override
	int weight(String a, String b) {
		return 1;
	}
}
