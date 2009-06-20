package entity.infoField;

public class EmptyTag extends EmptyInfoField implements IndexedInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4183945747308024656L;

	@Override
	public String getName() {
		return Messages.getString("Category"); //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return Tag.maxLength;
	}

}
