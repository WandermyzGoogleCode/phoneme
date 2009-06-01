package entity.infoField;

public class EmptyNickName extends EmptyInfoField implements IndexedInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9096224814179073172L;

	@Override
	public String getName() {
		return "NickName";
	}

	@Override
	public int getMaxLength() {
		return NickName.maxLength;
	}

}
