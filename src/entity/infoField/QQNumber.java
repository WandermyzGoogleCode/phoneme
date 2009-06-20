package entity.infoField;

import java.util.regex.Pattern;

public class QQNumber extends EmptyQQNumber {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3633220370059662405L;
	public static final int maxLength = 20;
	private String number;

	public static boolean check(String number){
		return (number != null && number.length() <= maxLength && Pattern.matches(Messages.getString("QQNumber.PatternFormat"), number)); //$NON-NLS-1$
	}
	
	public QQNumber(String qnum)
	{
		if (!check(qnum))
			qnum = ""; //$NON-NLS-1$
		this.number=qnum;
	}
	
	public void setStringValue(String qnum)
	{
		if (!check(qnum))
			qnum = ""; //$NON-NLS-1$
		this.number=qnum;
	}

	@Override
	public String getStringValue() {
		return this.number;
	}
	
	@Override
	public boolean isEmpty() {
		return number.equals(""); //$NON-NLS-1$
	}

	@Override
	public String toIDString() {
		return getName()+":"+getStringValue(); //$NON-NLS-1$
	}
}
