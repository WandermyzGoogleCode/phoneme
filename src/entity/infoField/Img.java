package entity.infoField;

public class Img extends EmptyImg {
	private String url;

	public Img(String url)
	{
		//����ʽ
		//TODO
		this.url=url;
	}
	
	public void setStringValue(String email)
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
