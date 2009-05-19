package entity.infoField;

public class EmptyEmailAddr extends EmptyInfoField implements IdenticalInfoField 
{

	@Override
	public String getName() {
		return "EmailAddress";
	}
}
