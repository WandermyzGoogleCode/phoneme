package entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entity.infoField.BaseInfoFieldName;
import entity.infoField.CustomInfoFieldName;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldName;

public class UserInfo {
	private BaseUserInfo baseInfo = null;
	private CustomUserInfo customInfo = null;

	public BaseUserInfo getBaseInfo() {
		return baseInfo;
	}

	public CustomUserInfo getCustomInfo() {
		return customInfo;
	}

	public void setBaseInfo(BaseUserInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public void setCustomInfo(CustomUserInfo customInfo) {
		this.customInfo = customInfo;
	}

	public UserInfo(BaseUserInfo baseInfo) {
		this.baseInfo = baseInfo;
		customInfo = new CustomUserInfo();
	}

	public UserInfo(BaseUserInfo baseInfo, CustomUserInfo custmInfo) {
		this.baseInfo = baseInfo;
		this.customInfo = custmInfo;
	}

	public UserInfo() {
		baseInfo = new BaseUserInfo();
		customInfo = new CustomUserInfo();
	}

	/**
	 * ����һ��IDΪ�������͵�UserInfo���Ա㴴�����ص���ϵ��
	 * 
	 * @return
	 */
	public static UserInfo getNewLocalUser() {
		BaseUserInfo baseUserInfo = new BaseUserInfo();
		CustomUserInfo customUserInfo = new CustomUserInfo();
		baseUserInfo.setID(ID.getLocalRandID());
		return new UserInfo(baseUserInfo, customUserInfo);
	}

	/**
	 * ��������á���
	 * 
	 * @return
	 */
	public String getStringValue() {
		String res = "";
		if (baseInfo != null)
			res += baseInfo.getStringValue();
		if (customInfo != null)
			res += customInfo.getStringValue();
		return res;
	}

	/**
	 * �������������UserInfo�ֶε��޸ģ� ���ⲻ֪���ֶ�Ӧ�÷���BaseUserInfo����CustomUserInfo ���ز����Ƿ�ɹ�
	 * 
	 * @param name
	 * @param value
	 */
	public boolean setInfoField(InfoFieldName name, InfoField info) {
		if (BaseInfoFieldName.contains(name.name())) {
			baseInfo.setInfoField(name.name(), info);
			return true;
		} else if (CustomInfoFieldName.contains(name.name())) {
			if (customInfo == null)
				customInfo = new CustomUserInfo();
			customInfo.setInfoField(name.name(), info);
			return true;
		} else
			return false;
	}
	
	/**
	 * ��ʵֻ��Ҫ��˼򵥡���
	 * @param info
	 * @return
	 */
	public boolean setInfoField(InfoField info) {
		return setInfoField(InfoFieldName.get(info.getName()), info);
	}

	/**
	 * �øú�������ȡ�ֶ�
	 * 
	 * @param name
	 * @return
	 */
	public InfoField getInfoField(InfoFieldName name) {
		if (BaseInfoFieldName.contains(name.name()))
			return baseInfo.getInfoField(name.name());
		else if (CustomInfoFieldName.contains(name.name())
				&& customInfo != null)
			return customInfo.getInfoField(name.name());
		else
			return null;
	}

	public Set<String> getKeySet() {
		Set<String> res = new HashSet<String>();
		res.addAll(baseInfo.getKeySet());
		if (customInfo != null)
			res.addAll(customInfo.getKeySet());
		return res;
	}
	
	static public void main(String args[]){
		Set<String> ss = new UserInfo().getKeySet();
		System.out.println(ss.size());
	}
}
