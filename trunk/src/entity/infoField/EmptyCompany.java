package entity.infoField;

/**
 * 公司或者学校
 * @author Administrator
 *
 */
public class EmptyCompany extends EmptyInfoField implements IndexedInfoField 
{

	@Override
	public String getName() {
		return "Company";
	}
}
