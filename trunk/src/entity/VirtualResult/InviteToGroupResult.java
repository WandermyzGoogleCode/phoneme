package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.Group;
import entity.SimpleError;
import entity.infoField.IdenticalInfoField;

public class InviteToGroupResult extends OneTimeVirtualResult {
	private IdenticalInfoField un;
	private Group g;
	private String inviteInfo;

	public InviteToGroupResult(IdenticalInfoField un, Group g,
			String inviteInfo, LogicCenter center) {
		super(center);
		this.un = un;
		this.g = g;
		this.inviteInfo = inviteInfo;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else if (!center.getLoginUser().getID().equals(g.getAdminUserID()))
			setError(new SimpleError("admin ID not matched"));
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().inviteToGroup(center.getLoginUser().getID(), un, g, inviteInfo);
	}

}
