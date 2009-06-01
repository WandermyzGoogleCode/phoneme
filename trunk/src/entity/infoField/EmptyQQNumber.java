package entity.infoField;

/**
 * QQ这个IM的infoField
 * 其它的IM再说，反正都类似
 * @author Administrator
 *
 */
public abstract class EmptyQQNumber extends EmptyInfoField implements IdenticalInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8156132447457779049L;

	@Override
	public String getName() {
		return "QQNumber";
	}

	@Override
	public int getMaxLength() {
		return QQNumber.maxLength;
	}

}
