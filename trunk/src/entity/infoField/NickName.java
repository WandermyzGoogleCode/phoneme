package entity.infoField;

public class NickName extends EmptyNickName {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3947844432005571211L;
	private static int maxLength = 200;
	private String name;

	public static boolean check(String data){
		return data.length() <= maxLength;
	}
	
	public NickName(String name)
	{
		if (!check(name))
			name = "";
		this.name=name;
	}
	
	public void setStringValue(String name)
	{
		if (!check(name))
			name = "";
		this.name=name;
	}

	@Override
	public String getStringValue() {
		return this.name;
	}
	
	@Override
	public boolean isEmpty() {
		return !name.equals("");
	}
}
