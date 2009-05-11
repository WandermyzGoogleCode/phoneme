package entity;

public class UserInfo {
	private BaseUserInfo baseInfo;
	private CustomUserInfo customInfo;
	
	public BaseUserInfo getBaseInfo()
	{
		return baseInfo;
	}
	
	public CustomUserInfo getCustomInfo()
	{
		return customInfo;
	}
	
	public void setBaseInfo(BaseUserInfo baseInfo)
	{
		this.baseInfo = baseInfo;
	}
	
	public void setCustomInfo(CustomUserInfo customInfo)
	{
		this.customInfo = customInfo;
	}
}
