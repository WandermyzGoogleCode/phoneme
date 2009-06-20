package entity.infoField;

public class GroupDescription extends EmptyGroupDescription {
	/**
	 * 
	 */
	private static final long serialVersionUID = -135322858855669388L;
	public static final int maxLength = 1000;
	private String description;

	public static boolean check(String desc){
		if (desc == null)
			return false;
		return desc.length() <= maxLength;
	}
	
	public GroupDescription(String desc)
	{
		if (!check(desc))
			desc = ""; //$NON-NLS-1$
		this.description=desc;
	}
	
	public void setStringValue(String desc)
	{
		if (!check(desc))
			desc = ""; //$NON-NLS-1$
		this.description=desc;
	}

	@Override
	public String getStringValue() {
		return this.description;
	}
	
	@Override
	public boolean isEmpty() {
		return description.equals(""); //$NON-NLS-1$
	}
}
