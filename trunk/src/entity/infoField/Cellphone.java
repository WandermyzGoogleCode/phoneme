package entity.infoField;

import java.util.regex.Pattern;

public class Cellphone extends EmptyCellphone{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7291035141898489378L;
	private static final String nullNumber = "";
	private String number;
	
	public static boolean check(String data){
		//TODO 更加准确的检测
		return Pattern.matches("(\\+\\d{2})?\\d{11}", data);
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
}
