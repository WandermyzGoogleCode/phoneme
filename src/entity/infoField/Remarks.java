package entity.infoField;

public class Remarks extends EmptyRemarks {
	/**
	 * 
	 */
	private static final long serialVersionUID = -222450790560309357L;
	private String remarks;
	public static int maxLength = 2000;

	public static boolean check(String remarks){
		return (remarks != null && remarks.length() <= maxLength);
	}
	
	public Remarks(String rem)
	{
		if (!check(rem))
			rem = ""; //$NON-NLS-1$
		this.remarks=rem;
	}
	
	public void setStringValue(String rem)
	{
		if (!check(rem))
			rem = ""; //$NON-NLS-1$
		this.remarks=rem;
	}

	@Override
	public String getStringValue() {
		return this.remarks;
	}
	
	@Override
	public boolean isEmpty() {
		return remarks.equals(""); //$NON-NLS-1$
	}
}
