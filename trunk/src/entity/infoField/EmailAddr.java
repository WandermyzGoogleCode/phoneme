package entity.infoField;

public class EmailAddr extends EmptyEmailAddr
{
	private String addr;

	public EmailAddr(String email)
	{
		//检查格式
		//TODO
		this.addr=email;
	}
	
	public void setStringValue(String email)
	{
		//检查格式，然后赋值
		//TODO
		this.addr=email;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
