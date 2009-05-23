package entity;

import java.io.Serializable;

public class Password implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4823009801317584895L;
	String encodedValue;
	
	public Password(String value)
	{
		//检查格式，加密，赋值
		//TODO
	}
	
	public String getEncodedValue()
	{
		return encodedValue;
	}
	
	public void setNewPassword(String value)
	{
		//检查格式，加密，赋值
		//TODO		
	}
}
