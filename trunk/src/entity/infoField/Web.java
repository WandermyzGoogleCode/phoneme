package entity.infoField;

public class Web extends EmptyWeb {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7376365384406568419L;
	private String web;
	public static final int maxLength = 500;
	
	public static boolean check(String web){
		return (web != null && web.length() <= maxLength);
	}

	public Web(String web)
	{
		if (!check(web))
			web = "";
		this.web = web;
	}
	
	public void setStringValue(String web)
	{
		if (!check(web))
			web = "";
		this.web = web;
	}

	@Override
	public String getStringValue() {
		return web;
	}
	
	@Override
	public boolean isEmpty() {
		return web.equals("");
	}
}
