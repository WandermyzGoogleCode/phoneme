package entity.infoField;

public class Phone extends EmptyPhone {
	private String number;

	public Phone(String number)
	{
		//����ʽ
		//TODO
		this.number=number;
	}
	
	public void setStringValue(String number)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.number=number;
	}

	@Override
	public String getStringValue() {
		return this.number;
	}
}
