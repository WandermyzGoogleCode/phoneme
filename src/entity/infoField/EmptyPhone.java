package entity.infoField;

/**
 * �̶��绰�����ڶ���绰������ο�{@link EmptyCellphone}
 * @author Administrator
 *
 */
public class EmptyPhone extends EmptyInfoField implements IndexedInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4109875034087236789L;

	@Override
	public String getName() {
		return "Phone";
	}

	@Override
	public int getMaxLength() {
		return Phone.maxLength;
	}

}
