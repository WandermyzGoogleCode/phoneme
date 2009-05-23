package entity.infoField;

import java.util.regex.Pattern;

/**
 * �����ʱ�򣬱�����yyyy-mm-dd�������ʽ���Ϸ�
 * @author Administrator
 *
 */
public class Birthday extends EmptyBirthday {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3884258647822327940L;
	private static final String nullData = "1111-11-11";
	private String day;
	private int y, m, d;
	private final int yyyy=0;
	private final int mm=1;
	private final int dd=2;
	
	public static boolean check(String data){
		return Pattern.matches("\\d+-\\d+-\\d+", data);
	}

	public boolean legalDate(){
		//TODO �пյĻ������ϸ��Ӿ�ȷ���ж�
		return (y >= 0 && y <= 9999 && m >= 1 && m <= 12 && d >= 1 && d <= 31);
	}
	
	public Birthday(String birthday)
	{
		//TODO TEST
		System.err.println(birthday);
		
		if(birthday==null || !check(birthday))
			birthday = nullData;
		
		String date[]=birthday.split("-");
		this.y=Integer.parseInt(date[yyyy]);
		this.m=Integer.parseInt(date[mm]);
		this.d=Integer.parseInt(date[dd]);
		this.day=birthday;
		if (!legalDate())
			setStringValue(nullData);
	}
	
	public void setStringValue(String birthday)
	{
		if(birthday==null || !check(birthday))
			birthday = nullData;
		
		String date[]=birthday.split("-");
		this.y=Integer.parseInt(date[yyyy]);
		this.m=Integer.parseInt(date[mm]);
		this.d=Integer.parseInt(date[dd]);
		this.day=birthday;
		if (!legalDate())
			setStringValue(nullData);
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
