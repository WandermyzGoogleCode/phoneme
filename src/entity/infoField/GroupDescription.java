package entity.infoField;

public class GroupDescription extends EmptyGroupDescription {
	/**
	 * 
	 */
	private static final long serialVersionUID = -135322858855669388L;
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
