package entity.infoField;

public class NickName extends EmptyNickName {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3947844432005571211L;
	private String name;

	public NickName(String name)
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
