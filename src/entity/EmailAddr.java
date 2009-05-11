package entity;

public class EmailAddr implements IdenticalInfoField 
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
	
	public String getName() {
		return new String("EmailAddress");
	}

	public String getStringValue() {
		return addr;
	}
}