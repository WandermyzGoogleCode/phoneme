package entity.infoField;

/**
 * 我们就不实现实际号码的数组了，
 * 因为数组过于麻烦而且现在的软件也几乎都不支持。
 * 如有需要，我们可以推出BaseUserInfo2.0, 3.0之类的，
 * 然后再里面加上"CellPhone2", "CellPhone3"之类的字段
 * @author Administrator
 *
 */
public class EmptyCellphone extends EmptyInfoField implements IdenticalInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5463180936829136629L;

	@Override
	public String getName() {
		return "CellPhone";
	}
}
