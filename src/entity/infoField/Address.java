package entity.infoField;

public class Address extends EmptyAddress {
	private String addr;

	public Address(String address)
	{
		//����ʽ
		//TODO 
		this.addr=address;
	}
	
	public void setStringValue(String address)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.addr=address;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
