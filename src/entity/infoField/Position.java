package entity.infoField;

public class Position extends EmptyPosition {
	private String position;

	public Position(String pos)
	{
		//检查格式
		//TODO
		this.position=pos;
	}
	
	public void setStringValue(String pos)
	{
		//检查格式，然后赋值
		//TODO
		this.position=pos;
	}

	@Override
	public String getStringValue() {
		return this.position;
	}
}
