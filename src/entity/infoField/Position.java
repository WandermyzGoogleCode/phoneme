package entity.infoField;

public class Position extends EmptyPosition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4354494388069835483L;
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
