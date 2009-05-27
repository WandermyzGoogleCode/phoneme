package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.SimpleError;

public class ApplyJoinGroupResult extends OneTimeVirtualResult {
	private ID gid;

	public ApplyJoinGroupResult(ID gid, LogicCenter center) {
		super(center);
		this.gid = gid;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().applyJoinGroup(center.getLoginUser().getID(), gid);
	}

}
