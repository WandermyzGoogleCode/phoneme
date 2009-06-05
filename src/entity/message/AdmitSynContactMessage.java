package entity.message;

import java.rmi.RemoteException;

import entity.BaseUserInfo;
import entity.ID;
import entity.UserInfo;
import entity.infoField.InfoFieldName;
import logiccenter.LogicCenter;

public class AdmitSynContactMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4879642549871197919L;
	private BaseUserInfo admitUser;
	private ID thisUser;
	int visibility;

	public AdmitSynContactMessage(BaseUserInfo userInfo, ID thisUser, int visibility,
			ID newMessageID) {
		super(newMessageID);
		this.thisUser = thisUser;
		this.visibility = visibility;
		admitUser = userInfo;
	}

	@Override
	public boolean autoProceed() {
		return true;
	}

	@Override
	public String detail() {
		return "用户："+admitUser.getStringValue()+"批准了你的申请，同意成为你的同步联系人。";
	}

	@Override
	public void subproceed(LogicCenter center) {
		center.getDataCenter().setUserInfo(new UserInfo(admitUser, null));//不更改本地信息
		center.getDataCenter().addSynRelationship(admitUser.getID());
		center.getAllContactsBox().editContact(new UserInfo(admitUser));
	}

	@Override
	public String title() {
		return "用户："+admitUser.getInfoField(InfoFieldName.Name.name())+"批准了你的申请";
	}

	@Override
	public String proceedName() {
		return "autoProceed";
	}
}
