package entity.infoField;

/**
 * 职位或者在读学位
 * @author Administrator
 *
 */
public class EmptyPosition extends EmptyInfoField implements IndexedInfoField 
{

	@Override
	public String getName() {
		return "Position";
	}

}
