package entity.infoField;

public class Img extends EmptyImg {
	private String url;

	public Img(String url)
	{
		//检查格式
		//TODO
		this.url=url;
	}
	
	public void setStringValue(String email)
	{
		//检查格式，然后赋值
		//TODO
		this.url=url;
	}

	@Override
	public String getStringValue() {
		return this.url;
	}
}
