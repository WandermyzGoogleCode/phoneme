package entity.infoField;

import java.text.DateFormat;
import java.util.Date;

import com.google.gdata.data.DateTime;


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
	public static final int maxLength = 20;
	private static final String nullData = ""; //$NON-NLS-1$
	private static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	private String day;
	private int y, m, d;
	private final int yyyy=0;
	private final int mm=1;
	private final int dd=2;
	
	static {
		dateFormat.setLenient(false);
	}
	
	public static boolean check(String data){
		if (data == null || data.length() > maxLength)
			return false;
		try{
			dateFormat.parse(data);
		}
		catch (java.text.ParseException e) {
			return false;
		}
		return true;
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

		String date[]=birthday.split("-"); //$NON-NLS-1$
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
		
		String date[]=birthday.split("-"); //$NON-NLS-1$
		this.y=Integer.parseInt(date[yyyy]);
		this.m=Integer.parseInt(date[mm]);
		this.d=Integer.parseInt(date[dd]);
		this.day=birthday;
	}

	@Override
	public String getStringValue() {
		if (isEmpty())
			return "";
		DateTime dt = new DateTime(getDate());
		dt.setDateOnly(true);
		return dt.toString();
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
	
	public static void main(String args[]){
		System.out.println(Birthday.check("2000-2-29")); //$NON-NLS-1$
		Birthday b = new Birthday("1989-7-9"); //$NON-NLS-1$
		System.out.println(b.getYear());
	}

	public Date getDate() {
		try {
			return dateFormat.parse(day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
