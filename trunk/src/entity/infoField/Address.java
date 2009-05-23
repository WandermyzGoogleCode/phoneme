package entity.infoField;

public class Address extends EmptyAddress {
	private String addr;

	public Address(String address)
	{
		//检查格式
		//TODO 
		this.addr=address;
	}
	
	public void setStringValue(String address)
	{
		//检查格式，然后赋值
		//TODO
		this.addr=address;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
