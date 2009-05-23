package entity.infoField;

public class Remarks extends EmptyRemarks {
	/**
	 * 
	 */
	private static final long serialVersionUID = -222450790560309357L;
	private String remarks;

	public Remarks(String rem)
	{
		//检查格式
		//TODO
		this.remarks=rem;
	}
	
	public void setStringValue(String rem)
	{
		//检查格式，然后赋值
		//TODO
		this.remarks=rem;
	}

	@Override
	public String getStringValue() {
		return this.remarks;
	}
}
