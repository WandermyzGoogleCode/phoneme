package entity.infoField;

/**
 * 公司或者学校
 * @author Administrator
 *
 */
public class EmptyCompany extends EmptyInfoField implements IndexedInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4142066825024758704L;

	@Override
	public String getName() {
		return "Company";
	}

	@Override
	public int getMaxLength() {
		return Company.maxLength;
	}
}
