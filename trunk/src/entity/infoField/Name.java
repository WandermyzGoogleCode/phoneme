package entity.infoField;

public class Name extends EmptyName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3189490709293556137L;
	public static final int maxLength = 50;
	private static final String nullName = Messages.getString("nullName"); //$NON-NLS-1$
	private String name;

	public static boolean check(String name){
		//TODO 有时间段话，更具体的检查
		return (name != null && name.length() > 0 && name.length() <= maxLength);
	}
	
	public Name(String name)
	{
		if (!check(name))
			name = nullName;
		this.name=name;
	}
	
	public void setStringValue(String name)
	{
		if (!check(name))
			name = nullName;
		this.name=name;
	}

	@Override
	public String getStringValue() {
		return this.name;
	}

	@Override
	public boolean isEmpty() {
		return name.equals(nullName);
	}
}
