package entity.infoField;

/**
 * �����ʱ�򣬱�����yyyy-mm-dd�������ʽ���Ϸ�
 * @author Administrator
 *
 */
public class Birthday extends EmptyBirthday {
	private String day;
	private int y, m, d;

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
