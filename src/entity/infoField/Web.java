package entity.infoField;

public class Web extends EmptyWeb {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7376365384406568419L;
	private String addr;

	public Web(String addr)
	{
		//����ʽ
		//TODO
		this.addr=addr;
	}
	
	public void setStringValue(String addr)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.addr=addr;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
