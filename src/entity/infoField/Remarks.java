package entity.infoField;

public class Remarks extends EmptyRemarks {
	/**
	 * 
	 */
	private static final long serialVersionUID = -222450790560309357L;
	private String remarks;

	public Remarks(String rem)
	{
		//����ʽ
		//TODO
		this.remarks=rem;
	}
	
	public void setStringValue(String rem)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.remarks=rem;
	}

	@Override
	public String getStringValue() {
		return this.remarks;
	}
}
