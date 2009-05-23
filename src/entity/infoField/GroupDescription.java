package entity.infoField;

public class GroupDescription extends EmptyGroupDescription {
	private String description;

	public GroupDescription(String desc)
	{
		//检查格式
		//TODO
		this.description=desc;
	}
	
	public void setStringValue(String desc)
	{
		//检查格式，然后赋值
		//TODO
		this.description=desc;
	}

	@Override
	public String getStringValue() {
		return this.description;
	}
}
