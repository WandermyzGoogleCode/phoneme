package entity.infoField;

public class Tag extends EmptyTag {
	private String tag;

	public Tag(String tag)
	{
		//����ʽ
		//TODO
		this.tag=tag;
	}
	
	public void setStringValue(String tag)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.tag=tag;
	}

	@Override
	public String getStringValue() {
		return this.tag;
	}
}
