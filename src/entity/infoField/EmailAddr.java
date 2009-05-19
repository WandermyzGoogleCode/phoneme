package entity.infoField;

public class EmailAddr extends EmptyEmailAddr
{
	private String addr;

	public EmailAddr(String email)
	{
		//检查格式
		//TODO
	}
	
	public void setNumber(String email)
	{
		//检查格式，然后赋值
		//TODO
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
