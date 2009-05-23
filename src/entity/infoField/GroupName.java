package entity.infoField;

public class GroupName extends EmptyGroupName {
	private String name;

	public GroupName(String name)
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
