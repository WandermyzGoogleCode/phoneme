package entity.infoField;

public class Name extends EmptyName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3189490709293556137L;
	private String name;

	public Name(String name)
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
