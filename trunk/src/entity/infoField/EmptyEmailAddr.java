package entity.infoField;

import entity.Messages;

public abstract class EmptyEmailAddr extends EmptyInfoField implements IdenticalInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229926790625369884L;

	@Override
	public String getName() {
		return entity.infoField.Messages.getString("EmailAddress");  //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return EmailAddr.maxLength;
	}
}
