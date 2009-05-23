package entity.infoField;

public class Name extends EmptyName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3189490709293556137L;
	private String name;

	public Name(String name)
	{
		//检查格式
		//TODO
		this.name=name;
	}
	
	public void setStringValue(String name)
	{
		//检查格式，然后赋值
		//TODO
		this.name=name;
	}

	@Override
	public String getStringValue() {
		return this.name;
	}
}
