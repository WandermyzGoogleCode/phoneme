package entity.infoField;

public class Img extends EmptyImg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -467731952290746685L;
	private String url;

	public Img(String url)
	{
		//����ʽ
		//TODO
		this.url=url;
	}
	
	public void setStringValue(String url)
	{
		//����ʽ��Ȼ��ֵ
		//TODO
		this.url=url;
	}

	@Override
	public String getStringValue() {
		return this.url;
	}
}
