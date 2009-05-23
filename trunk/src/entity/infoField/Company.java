package entity.infoField;

public class Company extends EmptyCompany {
	private String company;

	public Company(String company)
	{
		//检查格式
		//TODO
		this.company=company;
	}
	
	public void setStringValue(String company)
	{
		//检查格式，然后赋值
		//TODO
		this.company=company;
	}

	@Override
	public String getStringValue() {
		return company;
	}
}
