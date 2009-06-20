package entity.infoField;

import java.util.regex.Pattern;

public class Phone extends EmptyPhone {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7178975888690500192L;
	public static final int maxLength = 20;
	private String number;

	public static boolean check(String number){
		//TODO 有空的话，更详细的判断
		return (number != null && number.length() <= maxLength && Pattern.matches(Messages.getString("Phone.PatternFormat"), number)); //$NON-NLS-1$
	}
	
	public Phone(String number)
	{
		if (!check(number))
			number = ""; //$NON-NLS-1$
		this.number=number;
	}
	
	public void setStringValue(String number)
	{
		if (!check(number))
			number = ""; //$NON-NLS-1$
		this.number=number;
	}

	@Override
	public String getStringValue() {
		return this.number;
	}
	
	@Override
	public boolean isEmpty() {
		return number.equals(""); //$NON-NLS-1$
	}
}
