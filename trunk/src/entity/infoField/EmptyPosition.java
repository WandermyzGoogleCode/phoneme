package entity.infoField;

/**
 * ְλ�����ڶ�ѧλ
 * @author Administrator
 *
 */
public class EmptyPosition extends EmptyInfoField implements IndexedInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2847618387564878351L;

	@Override
	public String getName() {
		return Messages.getString("Position"); //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return Position.maxLength;
	}

}
