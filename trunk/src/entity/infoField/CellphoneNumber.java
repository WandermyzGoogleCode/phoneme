package entity.infoField;

public class CellphoneNumber extends EmptyCellphone{
	private String number;

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
