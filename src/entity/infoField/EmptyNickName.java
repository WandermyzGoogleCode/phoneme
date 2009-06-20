package entity.infoField;

import entity.Messages;

public class EmptyNickName extends EmptyInfoField implements IndexedInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9096224814179073172L;

	@Override
	public String getName() {
		return entity.infoField.Messages.getString("NickName");  //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return NickName.maxLength;
	}

}
