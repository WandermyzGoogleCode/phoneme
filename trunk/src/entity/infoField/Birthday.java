package entity.infoField;

/**
 * �����ʱ�򣬱�����yyyy-mm-dd�������ʽ���Ϸ�
 * @author Administrator
 *
 */
public class Birthday extends EmptyBirthday {
	private String day;
	private int y, m, d;
	private final int yyyy=0;
	private final int mm=1;
	private final int dd=2;

	public Birthday(String birthday)
	{
		//����ʽ
		//TODO 
		String date[]=birthday.split("-");
		this.y=Integer.parseInt(date[yyyy]);
		this.m=Integer.parseInt(date[mm]);
		this.d=Integer.parseInt(date[dd]);
		this.day=birthday;
	}
	
	public void setStringValue(String birthday)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		String date[]=birthday.split("-");
		this.y=Integer.parseInt(date[yyyy]);
		this.m=Integer.parseInt(date[mm]);
		this.d=Integer.parseInt(date[dd]);
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
