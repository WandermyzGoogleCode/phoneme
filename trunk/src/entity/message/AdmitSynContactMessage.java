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
		return "�û���"+admitUser.getStringValue()+"��׼��������룬ͬ���Ϊ���ͬ����ϵ�ˡ�";
	}

	@Override
	public void subproceed(LogicCenter center) {
		center.getDataCenter().setUserInfo(new UserInfo(admitUser, null));//�����ı�����Ϣ
		center.getDataCenter().addSynRelationship(admitUser.getID());
		center.getAllContactsBox().editContact(new UserInfo(admitUser));
	}

	@Override
	public String title() {
		return "�û���"+admitUser.getInfoField(InfoFieldName.Name.name())+"��׼���������";
	}

	@Override
	public String proceedName() {
		return "autoProceed";
	}
}
