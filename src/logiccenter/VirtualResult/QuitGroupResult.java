package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;

public class QuitGroupResult extends OneTimeVirtualResult {
	ID gid;
	String reason;

	public QuitGroupResult(ID gid, String reason, LogicCenter center) {
		super(center);
		this.gid = gid;
		this.reason = reason;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException{
		BoolInfo res = center.getServer().quitGroup(center.getLoginUser().getID(), gid, reason);
		if (res.isTrue()){
			center.getDataCenter().removeGroup(center.getGroup(gid));
			center.getAllGroupsBox().removeGroup(gid);
		}
		return res;
	}
}
