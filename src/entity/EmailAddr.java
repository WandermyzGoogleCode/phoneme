package entity;

public class EmailAddr implements IdenticalInfoField 
{
	private String addr;

	public EmailAddr(String email)
	{
		//����ʽ
		//TODO
	}
	
	public void setNumber(String email)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
	}
	
	public String getName() {
		return new String("EmailAddress");
	}

	public String getStringValue() {
		return addr;
	}
}