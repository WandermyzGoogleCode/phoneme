package entity.message;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.ID;
import entity.infoField.InfoFieldName;

public class ApplySynContactMessage extends Message {
	private BaseUserInfo applyUser;//�����ߣ�
	private ID targetUser;//��׼�����ߣ�������Ϣ��Ŀ�귢���ˣ�

	public ApplySynContactMessage(BaseUserInfo applyUser, ID targetUser, ID mID){
		super(mID);
		this.applyUser = applyUser;
		this.targetUser = targetUser;
	}
	
	@Override
	public void proceed(LogicCenter center) {
		// TODO Auto-generated method stub
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
	public void ignore(LogicCenter center) {
		// TODO Auto-generated method stub
	}
}
