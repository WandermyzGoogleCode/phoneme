package entity.infoField;

public class Company extends EmptyCompany {
	private String company;

	public Company(String company)
	{
		//����ʽ
		//TODO
		this.company=company;
	}
	
	public void setStringValue(String company)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.company=company;
	}

	@Override
	public String getStringValue() {
		return company;
	}
}
