package entity.infoField;

/**
 * 传入的时候，必须是yyyy-mm-dd，否则格式不合法
 * @author Administrator
 *
 */
public class Birthday extends EmptyBirthday {
	private String day;
	private int y, m, d;

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
	
	public int getYear(){
		return y;
	}
	
	public int getMonth(){
		return m;
	}
	
	public int getDay(){
		return d;
	}
}
