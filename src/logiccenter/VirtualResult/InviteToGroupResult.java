package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
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
			setError(ErrorType.NOT_LOGIN);
		else if (!center.getLoginUser().getID().equals(g.getAdminUserID()))
			setError(ErrorType.NOT_ADMIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().inviteToGroup(center.getLoginUser().getID(), un, g.getID(), inviteInfo);
	}

}
