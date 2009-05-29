package entity.message;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.ID;
import entity.infoField.InfoFieldName;

public class ApplySynContactMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6760010094612097372L;
	private BaseUserInfo applyUser;//申请者，
	private ID targetUser;//批准加入者（即该信息的目标发送人）
	private boolean proceeded = false;

	public ApplySynContactMessage(BaseUserInfo applyUser, ID targetUser, ID mID){
		super(mID);
		this.applyUser = applyUser;
		this.targetUser = targetUser;
	}
	
	@Override
	public void proceed(LogicCenter center) {
		center.getUI().addPerContact(applyUser);
		proceeded = true;
	}

	@Override
	public String detail() {
		return "用户："+applyUser.getStringValue()+" 想成为您的被授权联系人，随时同步你的信息。您可以设置给予他哪些您的信息。";
	}
	
	@Override
	public String title() {
		return applyUser.getInfoField(InfoFieldName.Name.name())+"申请成为被授权联系人";
	}

	@Override
	public boolean autoProceed() {
		return false;
	}

	@Override
	public boolean proceeded() {
		return this.proceeded;
	}
}
