package entity;

public class MobilePhoneNumber implements IdenticalInfoField {
	private String number;

	public MobilePhoneNumber(String number)
	{
		//检查格式
		//TODO
	}
	
	public void setNumber(String number)
	{
		//检查格式，然后赋值
		//TODO
	}
	
	public String getName() {
		return new String("MobilePhoneNumber");
	}

	public String getStringValue() {
		return number;
	}
}
