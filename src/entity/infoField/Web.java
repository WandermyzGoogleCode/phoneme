package entity.infoField;

public class Web extends EmptyWeb {
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
