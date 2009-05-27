package entity.infoField;

import java.util.regex.Pattern;

public class QQNumber extends EmptyQQNumber {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3633220370059662405L;
	private String number;

	public static boolean check(String number){
		return (number != null && Pattern.matches("\\d+", number));
	}
	
	public QQNumber(String qnum)
	{
		if (!check(qnum))
			qnum = "";
		this.number=qnum;
	}
	
	public void setStringValue(String qnum)
	{
		if (!check(qnum))
			qnum = "";
		this.number=qnum;
	}

	@Override
	public String getStringValue() {
		return this.number;
	}
	
	@Override
	public boolean isEmpty() {
		return !number.equals("");
	}
}
