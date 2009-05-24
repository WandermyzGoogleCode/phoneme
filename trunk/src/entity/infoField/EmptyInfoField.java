package entity.infoField;

/**
 * 空的字段，不做任何格式检查，只在新建一个空的UserInfo的时候使用。当有具体字段定义的时候，
 * 该字段即被替换。
 * @author Administrator
 *
 */
public abstract class EmptyInfoField implements InfoField {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8371556469263751670L;

	@Override
	public String getStringValue() {
		return "";
	}
	
	/**
	 * 默认为真，某些特殊情况下才为假，比如NoName, 1111-11-11。这些东西
	 * 虽然看起来不为空，但是其实是无效的。
	 */
	@Override
	public boolean isValid() {
		return true;
	}
}
