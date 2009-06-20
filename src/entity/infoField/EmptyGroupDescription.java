package entity.infoField;

public class EmptyGroupDescription extends EmptyInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5506546936094936481L;

	@Override
	public String getName() {
		return Messages.getString("GroupDescription"); //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return GroupDescription.maxLength;
	}

}
