package entity.infoField;

public class Tag extends EmptyTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7120471122733906974L;
	private String tag;
	public static final int maxLength = 50;
	
	public static boolean check(String tag){
		return (tag != null && tag.length() <= maxLength);
	}

	public Tag(String tag)
	{
		if (!check(tag))
			tag = ""; //$NON-NLS-1$
		this.tag=tag;
	}
	
	public void setStringValue(String tag)
	{
		if (!check(tag))
			tag = ""; //$NON-NLS-1$
		this.tag=tag;
	}

	@Override
	public String getStringValue() {
		return this.tag;
	}
	
	@Override
	public boolean isEmpty() {
		return tag.equals(""); //$NON-NLS-1$
	}
}
