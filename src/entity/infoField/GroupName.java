package entity.infoField;

import entity.Messages;

public class GroupName extends EmptyGroupName {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8471578597892895255L;
	public static final int maxLength = 50;
	private static final String nullName = entity.infoField.Messages.getString("nullName");  //$NON-NLS-1$
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
