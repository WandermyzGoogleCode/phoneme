package algorithm;

/**
 * 一些常用的关于字符串的函数
 * @author Administrator
 *
 */
public class StringFunc {
	/**
	 * 最长公共子序列， longest common subsequence
	 * @return
	 */
	public static int lcsq(String a, String b){
		int n = a.length();
		int m = b.length();
		int ans[][] = new int[n+1][m+1];
		for(int i=1; i<=n; i++)
			for(int j=1; j<=m; j++)
			{
				ans[i][j] = Math.max(ans[i-1][j-1]+((a.charAt(i-1) == b.charAt(j-1)) ? 1 : 0), ans[i][j-1]);
				ans[i][j] = Math.max(ans[i][j], ans[i-1][j]);
			}
		return ans[n][m];
	}
	
	/**
	 * 最长公共子串， longest common substring
	 * @param a
	 * @param b
	 * @return
	 */
	public static int lcst(String a, String b){
		int n = a.length();
		int m = b.length();
		int ans[][] = new int[n+1][m+1];
		int res = 0;
		for(int i=1; i<=n; i++)
			for(int j=1; j<=m; j++)
			{
				if (a.charAt(i-1) == b.charAt(j-1))
					ans[i][j] = ans[i-1][j-1]+1;
				res = Math.max(ans[i][j], res);
			}
		return res;
	}
	
	public static void main(String args[]){
		String as[] = {"asdfasdf", "SpaceFlyer", "13263255515"};
		String bs[] = {"adafas", "aceyer", "13575754245"};
		for(int i=0; i<as.length; i++)
			System.out.println(new Integer(lcsq(as[i], bs[i])).toString()+" "+new Integer(lcst(as[i], bs[i])).toString());
	}
}
