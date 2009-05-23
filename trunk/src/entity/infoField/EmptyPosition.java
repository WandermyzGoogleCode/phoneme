package entity.infoField;

/**
 * 职位或者在读学位
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
		return "Position";
	}

}
