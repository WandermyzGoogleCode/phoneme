package entity.infoField;

public class EmailAddr extends EmptyEmailAddr
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3297353199701838427L;
	private String addr;

	public EmailAddr(String email)
	{
		//����ʽ
		//TODO
		this.addr=email;
	}
	
	public void setStringValue(String email)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.addr=email;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
