package entity.infoField;

import java.util.regex.Pattern;

public class EmailAddr extends EmptyEmailAddr
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3297353199701838427L;
	public static final int maxLength = 100;
	private static final String nullAddr = ""; //$NON-NLS-1$
	private String addr;

	public static boolean check(String data){
		if (data == null || data.length() > maxLength)
			return false;
		return Pattern.matches(Messages.getString("EmailAddr.PatternFormat"), data); //$NON-NLS-1$
	}
	
	public EmailAddr(String email)
	{
		if (!check(email))
			email = nullAddr;
		this.addr=email;
	}
	
	public void setStringValue(String email)
	{
		if (!check(email))
			email = nullAddr;
		this.addr=email;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
	
	@Override
	public boolean isEmpty() {
		return addr.equals(nullAddr);
	}

	@Override
	public String toIDString() {
		return getName()+":"+getStringValue(); //$NON-NLS-1$
	}
}
