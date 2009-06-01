package entity.infoField;

/**
 * 固定电话，关于多个电话的问题参考{@link EmptyCellphone}
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
