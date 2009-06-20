package entity.infoField;

import java.util.regex.Pattern;

public class Cellphone extends EmptyCellphone{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7291035141898489378L;
	public static final int maxLength = 20;
	private static final String nullNumber = ""; //$NON-NLS-1$
	private String number;
	
	public static boolean check(String data){
		//TODO 有空的话，更加准确的检测
		if (data == null || data.length() > maxLength)
			return false;
		return Pattern.matches(Messages.getString("Cellphone.PatternFormat"), data); //$NON-NLS-1$
	}

	public Cellphone(String number)
	{
		if (number == null || !check(number))
			number = nullNumber;
		this.number=number;
	}
	
	public void setStringValue(String number)
	{
		if (number == null || !check(number))
			number = nullNumber;
		this.number=number;
	}

	@Override
	public String getStringValue() {
		return number;
	}
	
	@Override
	public boolean isEmpty() {
		return number.equals(nullNumber);
	}

	@Override
	public String toIDString() {
		return getName()+":"+getStringValue(); //$NON-NLS-1$
	}
}
