package algorithm;

public interface Matcher {
	/**
	 * @param pattern 模式对象
	 * @param target  实际要匹配的对象
	 * @return
	 */
	public boolean match(Object pattern, Object target);
}
