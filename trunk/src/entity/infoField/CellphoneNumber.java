package entity.infoField;

public class CellphoneNumber extends EmptyCellphone{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7291035141898489378L;
	private String number;

	public CellphoneNumber(String number)
	{
		//����ʽ
		//TODO
		this.number=number;
	}
	
	public void setStringValue(String number)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.number=number;
	}

	@Override
	public String getStringValue() {
		return number;
	}
}
