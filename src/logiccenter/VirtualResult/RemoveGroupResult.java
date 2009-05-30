package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;

public class RemoveGroupResult extends OneTimeVirtualResult {
	private Group g;

	public RemoveGroupResult(Group g, LogicCenter center) {
		super(center);
		this.g = g;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else if (!center.getLoginUser().getID().equals(g.getAdminUserID()))
			setError(ErrorType.NOT_ADMIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		BoolInfo res = center.getServer().removeGroup(center.getLoginUser().getID(), g.getID());
		if (res.isTrue()){
			center.getDataCenter().removeGroup(g);
			center.getAllGroupsBox().removeGroup(g.getID());
		}
		return res;
	}
}
