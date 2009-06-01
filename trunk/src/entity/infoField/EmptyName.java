package entity.infoField;

/**
 * 全名，first name和second name的剥离可以
 * 在具体的Name类型中（比如中国NAME,XX国NAME），放一个STRATEGY，
 * 来控制名字的分割。然后提供getFirstName和getLastName的接口。
 * @author Administrator
 *
 */
public class EmptyName extends EmptyInfoField implements IndexedInfoField 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4622220009761467875L;

	@Override
	public String getName() {
		return "Name";
	}

	@Override
	public int getMaxLength() {
		return Name.maxLength;
	}

}
