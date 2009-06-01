package entity.infoField;

public class EmptyBirthday extends EmptyInfoField implements IndexedInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6721268565699371225L;

	@Override
	public String getName() {
		return "Birthday";
	}

	@Override
	public int getMaxLength() {
		return Birthday.maxLength;
	}
}
