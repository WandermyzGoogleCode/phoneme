package entity.message;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.ID;
import entity.UserInfo;

public class ContactUpdatedMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3335879499960140669L;
	BaseUserInfo contact;

	public ContactUpdatedMessage(BaseUserInfo contact, ID mID){
		super(mID);
		this.contact = contact;
	}
	
	@Override
	public boolean autoProceed() {
		return true;
	}

	@Override
	public String detail() {
		return "��ϵ�ˣ�"+contact.getStringValue()+"��������Ϣ";
	}

	@Override
	public void proceed(LogicCenter center) {
		UserInfo newInfo = new UserInfo(contact);
		newInfo.setCustomInfo(null);//���޸�custom���ֶ�
		center.getDataCenter().setUserInfo(newInfo);
		center.getAllContactsBox().editContact(newInfo);
		proceeded = true;
	}

	@Override
	public String proceedName() {
		return "autoProceed";
	}

	@Override
	public String title() {
		return "��ϵ�ˣ�"+contact.getName()+"��������Ϣ";
	}

}
