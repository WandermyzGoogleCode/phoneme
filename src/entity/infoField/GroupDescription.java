package entity.infoField;

public class GroupDescription extends EmptyGroupDescription {
	private String description;

	public GroupDescription(String desc)
	{
		//����ʽ
		//TODO
		this.description=desc;
	}
	
	public void setStringValue(String desc)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.description=desc;
	}

	@Override
	public String getStringValue() {
		return this.description;
	}
}
