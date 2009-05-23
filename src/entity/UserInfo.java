package entity;

public class UserInfo {
	private BaseUserInfo baseInfo = null;
	private CustomUserInfo customInfo = null;
	
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
	
	public UserInfo(BaseUserInfo baseInfo){
		this.baseInfo = baseInfo;
	}
	
	public UserInfo(){}
}
