package entity.infoField;

public class Position extends EmptyPosition {
	private String position;

	public Position(String pos)
	{
		//����ʽ
		//TODO
		this.position=pos;
	}
	
	public void setStringValue(String pos)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.position=pos;
	}

	@Override
	public String getStringValue() {
		return this.position;
	}
}
