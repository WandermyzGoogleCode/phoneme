package entity.infoField;

import entity.Messages;

/**
 * ±¸×¢
 * @author Administrator
 *
 */
public class EmptyRemarks extends EmptyInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676824303150943417L;

	@Override
	public String getName() {
		return entity.infoField.Messages.getString("Remarks");  //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return Remarks.maxLength;
	}

}
