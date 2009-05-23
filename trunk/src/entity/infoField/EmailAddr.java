package entity.infoField;

import java.util.regex.Pattern;

public class EmailAddr extends EmptyEmailAddr
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3297353199701838427L;
	private static final String nullAddr = "";
	private String addr;

	public static boolean check(String data){
		return Pattern.matches("\\w+@\\w+.\\w+", data);
	}
	
	public EmailAddr(String email)
	{
		if (email == null || !check(email))
			email = nullAddr;
		this.addr=email;
	}
	
	public void setStringValue(String email)
	{
		if (email == null || !check(email))
			email = nullAddr;
		this.addr=email;
	}

	@Override
	public String getStringValue() {
		return addr;
	}
}
