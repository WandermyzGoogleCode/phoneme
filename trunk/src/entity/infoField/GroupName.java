package entity.infoField;

public class GroupName extends EmptyGroupName {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8471578597892895255L;
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
