package entity.infoField;

public class Web extends EmptyWeb {
	private String addr;

	public Web(String addr)
	{
		//检查格式
		//TODO
		this.addr=addr;
	}
	
	public void setStringValue(String addr)
	{
		//检查格式，然后赋值
		//TODO
		this.addr=addr;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
