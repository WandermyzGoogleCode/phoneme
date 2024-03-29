package entity.infoField;

public class Img extends EmptyImg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -467731952290746685L;
	private String url;
	public static final int maxLength = 500;

	public static boolean check(String url){
		//TODO 更精确的检查
		return (url != null && url.length() <= maxLength);
	}
	
	public Img(String url)
	{
		if (!check(url))
			url = ""; //$NON-NLS-1$
		this.url=url;
	}
	
	public void setStringValue(String url)
	{
		if (!check(url))
			url = ""; //$NON-NLS-1$
		this.url=url;
	}

	@Override
	public String getStringValue() {
		return this.url;
	}
	
	@Override
	public boolean isEmpty() {
		return url.equals(url);
	}
}
