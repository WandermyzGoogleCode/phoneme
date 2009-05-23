package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ID;
import entity.SimpleError;

public class QuitGroupResult extends OneTimeVirtualResult {
	ID gid;
	String reason;

	public QuitGroupResult(ID gid, String reason, LogicCenter center) {
		super(center);
		this.gid = gid;
		this.reason = reason;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException{
		//TODO 退出成功后，维护相关群组信息，以及用户所在群组信息
		return center.getServer().quitGroup(center.getLoginUser().getID(), gid, reason);
	}

}
