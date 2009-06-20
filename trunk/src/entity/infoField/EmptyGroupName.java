package entity.infoField;

/**
 * 群组的名字
 * @author Administrator
 *
 */
public class EmptyGroupName extends EmptyInfoField implements IndexedInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6879903301153747501L;

	@Override
	public String getName() {
		return Messages.getString("GroupName"); //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return GroupName.maxLength;
	}

}
