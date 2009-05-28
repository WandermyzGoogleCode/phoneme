package entity.infoField;

import java.util.regex.Pattern;

/**
 * 传入的时候，必须是yyyy-mm-dd，否则格式不合法
 * @author Administrator
 *
 */
public class Birthday extends EmptyBirthday {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3884258647822327940L;
	private static final String nullData = "";
	private String day;
	private int y, m, d;
	private final int yyyy=0;
	private final int mm=1;
	private final int dd=2;
	
	public static boolean check(String data){
		//TODO 有空的话，加上更加精确的判断
		if (data == null)
			return false;
		return Pattern.matches("\\d+-\\d+-\\d+", data);
	}

	private void setEmpty(){
		day = nullData;
		y = m = d = 0;
	}
	
	public Birthday(String birthday)
	{
		if(!check(birthday)){
			setEmpty();
			return;
		}
		
		String date[]=birthday.split("-");
		this.y=Integer.parseInt(date[yyyy]);
		this.m=Integer.parseInt(date[mm]);
		this.d=Integer.parseInt(date[dd]);
		this.day=birthday;
	}
	
	public void setStringValue(String birthday)
	{
		if(!check(birthday)){
			setEmpty();
			return;
		}
		
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
	
	@Override
	public boolean isEmpty(){
		return (day.equals(nullData));
	}
}
