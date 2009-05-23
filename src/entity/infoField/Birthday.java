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
	private String day;
	private int y, m, d;
	private final int yyyy=0;
	private final int mm=1;
	private final int dd=2;
	
	boolean check(String data){
		return Pattern.matches("\\d+-\\d+-\\d", data);
	}
	
	public Birthday(String birthday)
	{
		if(birthday!=null && check(birthday)){
			String date[]=birthday.split("-");
			this.y=Integer.parseInt(date[yyyy]);
			this.m=Integer.parseInt(date[mm]);
			this.d=Integer.parseInt(date[dd]);
			this.day=birthday;
		}else{
			this.day="0-0-0";
			this.y=0;
			this.m=0;
			this.d=0;
		}
	}
	
	public void setStringValue(String birthday)
	{
		if(birthday!=null && check(birthday)){
			String date[]=birthday.split("-");
			this.y=Integer.parseInt(date[yyyy]);
			this.m=Integer.parseInt(date[mm]);
			this.d=Integer.parseInt(date[dd]);
			this.day=birthday;
		}
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
