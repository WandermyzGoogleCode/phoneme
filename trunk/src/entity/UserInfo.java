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
	
	public UserInfo(BaseUserInfo baseInfo, CustomUserInfo custmInfo){
		this.baseInfo = baseInfo;
		this.customInfo = custmInfo;
	}
	
	public UserInfo(){}
	
	/**
	 * ����һ��IDΪ�������͵�UserInfo���Ա㴴�����ص���ϵ��
	 * @return
	 */
	public static UserInfo getNewLocalUser(){
		BaseUserInfo baseUserInfo = new BaseUserInfo();
		CustomUserInfo customUserInfo = new CustomUserInfo();
		baseUserInfo.setID(ID.getLocalRandID());
		return new UserInfo(baseUserInfo, customUserInfo);
	}
	
	public String getStringValue(){
		String res = "";
		if (baseInfo != null)
			res += baseInfo.getStringValue();
		if (customInfo != null)
			res += customInfo.getStringValue();
		return res;
	}
}
