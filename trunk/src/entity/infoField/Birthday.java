package entity.infoField;

public class Birthday extends EmptyBirthday {
	private String day;

	public Birthday(String birthday)
	{
		//检查格式
		//TODO 
		this.day=birthday;
	}
	
	public void setStringValue(String birthday)
	{
		//检查格式，然后赋值
		//TODO
		this.day=birthday;
	}

	@Override
	public String getStringValue() {
		return day;
	}
}
