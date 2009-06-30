package algorithm;

import java.util.HashMap;
import java.util.Map;

public class LCSQUserInfoMatcher extends UserInfoMatcher {
	@Override
	int similarity(String p, String t) {
		//”≈ªØ
//		if (p.equals(t))
//			return p.length();
//		Map<Character, Integer> charCntP = new HashMap<Character, Integer>(), charCntT = new HashMap<Character, Integer>();
//		for(int i=0; i<p.length(); i++)
//			if (charCntP.containsKey(p.charAt(i)))
//				charCntP.put(p.charAt(i), (charCntP.get(p.charAt(i)).intValue())+1);
//			else
//				charCntP.put(p.charAt(i), 1);
//		for(int i=0; i<t.length(); i++)
//			if (charCntT.containsKey(t.charAt(i)))
//				charCntT.put(t.charAt(i), (charCntT.get(t.charAt(i)).intValue())+1);
//			else
//				charCntT.put(t.charAt(i), 1);
//		int minDiff = 0;
//		for(Character c: charCntP.keySet())
//			minDiff += Math.max(charCntP.get(c)-(charCntT.containsKey(c) ? charCntT.get(c) : 0), 0);
//		if (minDiff >= p.length()-1)
//			return p.length()-minDiff;
		
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
