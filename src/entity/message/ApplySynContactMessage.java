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
	private BaseUserInfo applyUser;//�����ߣ�
	private ID targetUser;//��׼�����ߣ�������Ϣ��Ŀ�귢���ˣ�
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
		return "�û���"+applyUser.getStringValue()+" ���Ϊ���ı���Ȩ��ϵ�ˣ���ʱͬ�������Ϣ�����������ø�������Щ������Ϣ��";
	}
	
	@Override
	public String title() {
		return applyUser.getInfoField(InfoFieldName.Name.name())+"�����Ϊ����Ȩ��ϵ��";
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
