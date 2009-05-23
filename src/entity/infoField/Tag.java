package entity.infoField;

public class Tag extends EmptyTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7120471122733906974L;
	private String tag;

	public Tag(String tag)
	{
		//检查格式
		//TODO
		this.tag=tag;
	}
	
	public void setStringValue(String tag)
	{
		//检查格式，然后赋值
		//TODO
		this.tag=tag;
	}

	@Override
	public String getStringValue() {
		return this.tag;
	}
}
