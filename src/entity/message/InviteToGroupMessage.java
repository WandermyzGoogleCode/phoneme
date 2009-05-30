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
		return "群组："+g.getName()+"邀请您加入。您可以设置对于群组的信息权限设置，以及与群组的关系可见度设置。";
	}

	@Override
	public void proceed(LogicCenter center) throws RemoteException,
			MyRemoteException {
		center.getUI().admitInvitation(g);
		proceeded = true;
	}

	@Override
	public String proceedName() {
		return "接受邀请";
	}

	@Override
	public String title() {
		return "群组："+g.getName()+"邀请您加入";
	}

}
