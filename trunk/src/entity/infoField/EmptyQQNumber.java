package entity.infoField;

/**
 * QQ���IM��infoField
 * ������IM��˵������������
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
