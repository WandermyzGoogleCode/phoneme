package entity.message;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;

public class InviteToGroupMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4612089024111290224L;
	Group g;
	String inviteInfo;
	ID invitedUser;

	public InviteToGroupMessage(ID invitedUser, Group g, String inviteInfo, ID mID){
		super(mID);
		this.g = g;
		this.invitedUser = invitedUser;
		this.inviteInfo = inviteInfo;
	}
	
	@Override
	public boolean autoProceed() {
		return false;
	}

	@Override
	public String detail() {
		return "Ⱥ�飺"+g.getName()+"���������롣���������ö���Ⱥ�����ϢȨ�����ã��Լ���Ⱥ��Ĺ�ϵ�ɼ������á�";
	}

	@Override
	public void proceed(LogicCenter center) throws RemoteException,
			MyRemoteException {
		center.getUI().admitInvitation(g);
		proceeded = true;
	}

	@Override
	public String proceedName() {
		return "��������";
	}

	@Override
	public String title() {
		return "Ⱥ�飺"+g.getName()+"����������";
	}

}
