package entity.infoField;

/**
 * ¸öÈËÖ÷Ò³£¿
 * @author Administrator
 *
 */
public class EmptyWeb extends EmptyInfoField implements IndexedInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9040832929014130964L;

	@Override
	public String getName() {
		return Messages.getString("Website"); //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return Web.maxLength;
	}

}
