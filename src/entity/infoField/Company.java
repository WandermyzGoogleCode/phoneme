package entity.infoField;

public class Company extends EmptyCompany {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9079041943593432133L;

	public static int maxLength = 200;

	private String company;

	static public boolean check(String company){
		if (company == null)
			return false;
		return company.length() <= maxLength;
	}
	
	public Company(String company)
	{
		if (!check(company))
			company = "";
		this.company=company;
	}
	
	public void setStringValue(String company)
	{
		if (!check(company))
			company = "";
		this.company=company;
	}

	@Override
	public String getStringValue() {
		return company;
	}
	
	@Override
	public boolean isEmpty() {
		return company.equals("");
	}
}
