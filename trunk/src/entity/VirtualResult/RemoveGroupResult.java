package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.Group;
import entity.SimpleError;

public class RemoveGroupResult extends OneTimeVirtualResult {
	private Group g;

	public RemoveGroupResult(Group g, LogicCenter center) {
		super(center);
		this.g = g;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else if (!center.getLoginUser().getID().equals(g.getAdminUserID()))
			setError(new SimpleError("admin ID not matched"));
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO 成功以后，修改相应的群组信息，以及用户所创立的群组信息
		return center.getServer().removeGroup(center.getLoginUser().getID(), g);
	}

}
