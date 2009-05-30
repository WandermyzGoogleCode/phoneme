package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.Permission;

public class ApplyJoinGroupResult extends OneTimeVirtualResult {
	private ID gid;
	private Permission p;
	private int visibility;

	public ApplyJoinGroupResult(ID gid, Permission p, int visibility, LogicCenter center) {
		super(center);
		this.p = p;
		this.visibility = visibility;
		this.gid = gid;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().applyJoinGroup(center.getLoginUser().getID(), gid, p, visibility);
	}

}
