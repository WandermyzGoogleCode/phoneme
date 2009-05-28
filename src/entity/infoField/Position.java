package entity.infoField;

public class Position extends EmptyPosition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4354494388069835483L;
	private static int maxLength = 500;
	private String position;

	public static boolean check(String position){
		return (position != null && position.length() <= maxLength);
	}
	
	public Position(String pos)
	{
		if (!check(pos))
			pos = "";
		this.position=pos;
	}
	
	public void setStringValue(String pos)
	{
		if (!check(pos))
			pos = "";
		this.position=pos;
	}

	@Override
	public String getStringValue() {
		return this.position;
	}
	
	@Override
	public boolean isEmpty() {
		return position.equals("");
	}
}
