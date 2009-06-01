package entity.infoField;

public class Address extends EmptyAddress {
	/**
	 * 
	 */
	private static final long serialVersionUID = 225686845640478206L;
	public static final int maxLength = 200;
	private String addr;

	static public boolean check(String addr){
		//TODO �����Ƶļ��
		if (addr == null)
			return false;
		return addr.length() <= maxLength;
	}
	
	public Address(String address)
	{
		if (!check(address))
			address = "";
		this.addr=address;
	}
	
	public void setStringValue(String address)
	{
		if (!check(address))
			address = "";
		this.addr=address;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
	
	@Override
	public boolean isEmpty() {
		return (addr.equals(""));
	}
}
