package entity.message;

import java.rmi.RemoteException;

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
	private int visibility;

	public ApplySynContactMessage(BaseUserInfo applyUser, ID targetUser, int visibility, ID mID){
		super(mID);
		this.applyUser = applyUser;
		this.targetUser = targetUser;
		this.visibility = visibility;
	}
	
	@Override
	public void proceed(LogicCenter center) throws RemoteException{
		center.getUI().addPerContact(applyUser);
		center.getServer().admitSynContact(center.getLoginUser().getID(), applyUser.getID(), visibility);
		if (!center.getDataCenter().getAllSynContactsID().contains(applyUser.getID()))
		{
			boolean res = center.getUI().yesOrNo("�Է��Ѿ���Ϊ��ı���Ȩ��ϵ�ˣ����������������ͬ����ϵ�ˣ�������Ҫ����Ϊͬ����ϵ����");
			if (res)
				center.getUI().addSynContact(applyUser);
		}
		proceeded = true;
	}

	@Override
	public String detail() {
		return "�û���"+applyUser.getStringValue()+" ���Ϊ���ı���Ȩ��ϵ�ˣ�"
			+"������ϵ�Ŀɼ�����Ϊ��"+Integer.toString(visibility)+"�����������ø�������Щ������Ϣ������ɾ�������롣";
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
	public String proceedName() {
		return "ͬ������";
	}
}
