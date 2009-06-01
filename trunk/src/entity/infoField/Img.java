package entity.infoField;

public class Img extends EmptyImg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -467731952290746685L;
	private String url;
	public static final int maxLength = 500;

	public static boolean check(String url){
		//TODO ����ȷ�ļ��
		return (url != null && url.length() <= maxLength);
	}
	
	public Img(String url)
	{
		if (!check(url))
			url = "";
		this.url=url;
	}
	
	public void setStringValue(String url)
	{
		if (!check(url))
			url = "";
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
