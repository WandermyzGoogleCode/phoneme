package entity.infoField;

public class GroupName extends EmptyGroupName {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8471578597892895255L;
	private static final int maxLength = 100;
	private static final String nullName = "NoName";
	private String name;

	public static boolean check(String name){
		return (name != null && name.length() <= maxLength && name.length() > 0);
	}
	
	public GroupName(String name)
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
