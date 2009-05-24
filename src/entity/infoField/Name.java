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
		if (name == null || name.length() == 0)
			name = "NoName";
		this.name=name;
	}
	
	public void setStringValue(String name)
	{
		//检查格式，然后赋值
		//TODO
		if (name == null || name.length() == 0)
			name = "NoName";
		this.name=name;
	}

	@Override
	public String getStringValue() {
		return this.name;
	}
	
	@Override
	public boolean isValid(){
		return (!name.equals("NoName"));
	}
}
