package entity.infoField;

public class Birthday extends EmptyBirthday {
	private String day;

	public Birthday(String birthday)
	{
		//����ʽ
		//TODO 
		this.day=birthday;
	}
	
	public void setStringValue(String birthday)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.day=birthday;
	}

	@Override
	public String getStringValue() {
		return day;
	}
}
