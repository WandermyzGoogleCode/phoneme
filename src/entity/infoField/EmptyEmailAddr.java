package entity.infoField;

public class EmptyEmailAddr extends EmptyInfoField implements IdenticalInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229926790625369884L;

	@Override
	public String getName() {
		return "EmailAddress";
	}
}
