package entity.message;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.ID;
import entity.MyRemoteException;
import entity.infoField.InfoFieldName;

public class ApplySynContactMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6760010094612097372L;
	private BaseUserInfo applyUser;//申请者，
	private ID targetUser;//批准加入者（即该信息的目标发送人）
	private int visibility;

	public ApplySynContactMessage(BaseUserInfo applyUser, ID targetUser, int visibility, ID mID){
		super(mID);
		this.applyUser = applyUser;
		this.targetUser = targetUser;
		this.visibility = visibility;
	}
	
	@Override
	public void subproceed(LogicCenter center) throws RemoteException, MyRemoteException{
		center.getUI().addPerContact(applyUser);
		System.out.println("milestone1");//TODO TEST
		center.getServer().admitSynContact(center.getLoginUser().getID(), applyUser.getID(), visibility);
		System.out.println("milestone2");//TODO TEST
		if (!center.getDataCenter().getAllSynContactsID().contains(applyUser.getID()))
		{
			boolean res = center.getUI().yesOrNo("对方已经成为你的被授权联系人，但是他还不是你的同步联系人，请问你要加他为同步联系人吗？");
			if (res)
				center.getUI().addSynContact(applyUser);
		}
		System.out.println("milestone3");//TODO TEST
	}

	@Override
	public String detail() {
		return "用户："+applyUser.getStringValue()+" 想成为您的被授权联系人，"
			+"并将关系的可见度设为："+Integer.toString(visibility)+"。您可以设置给予他哪些您的信息，或者删除该申请。";
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
	public String proceedName() {
		return "同意申请";
	}
}
