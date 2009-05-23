package entity.infoField;

public class QQNumber extends EmptyQQNumber {
	private String number;

	public QQNumber(String qnum)
	{
		//检查格式
		//TODO
		this.number=qnum;
	}
	
	public void setStringValue(String qnum)
	{
		//检查格式，然后赋值
		//TODO
		this.number=qnum;
	}

	@Override
	public String getStringValue() {
		return this.number;
	}
}
