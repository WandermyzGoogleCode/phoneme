package entity.infoField;

public class GroupName extends EmptyGroupName {
	private String name;

	public GroupName(String name)
	{
		//����ʽ
		//TODO
		this.name=name;
	}
	
	public void setStringValue(String name)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.name=name;
	}

	@Override
	public String getStringValue() {
		return this.name;
	}
}
