package entity.infoField;

public class QQNumber extends EmptyQQNumber {
	private String number;

	public QQNumber(String qnum)
	{
		//����ʽ
		//TODO
		this.number=qnum;
	}
	
	public void setStringValue(String qnum)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.number=qnum;
	}

	@Override
	public String getStringValue() {
		return this.number;
	}
}
