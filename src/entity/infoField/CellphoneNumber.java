package entity.infoField;

public class CellphoneNumber extends EmptyCellphone{
	private String number;

	public CellphoneNumber(String number)
	{
		//检查格式
		//TODO
	}
	
	public void setNumber(String number)
	{
		//检查格式，然后赋值
		//TODO
	}

	@Override
	public String getStringValue() {
		return number;
	}
}
