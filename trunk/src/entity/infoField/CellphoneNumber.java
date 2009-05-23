package entity.infoField;

import java.util.regex.Pattern;

public class CellphoneNumber extends EmptyCellphone{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7291035141898489378L;
	private String number;
	
	boolean check(String data){
		return Pattern.matches("+?(\\d\\d)?", data);
	}

	public CellphoneNumber(String number)
	{
		//检查格式
		//TODO
		this.number=number;
	}
	
	public void setStringValue(String number)
	{
		//检查格式，然后赋值
		//TODO
		this.number=number;
	}

	@Override
	public String getStringValue() {
		return number;
	}
}
