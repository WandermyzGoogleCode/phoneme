package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.SimpleError;

/**
 * 状态为PREPARED就表示已经成功操作
 * ERRORED表示操作出错
 * LOADING表示正在操作
 * @author Administrator
 *
 */
public class AdmitInvitationResult extends OneTimeVirtualResult {
	private ID gid;

	public AdmitInvitationResult(ID gid, LogicCenter center) {
		super(center);
		this.gid = gid;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO 如果成功，应该更新相关群组信息，以及用户所在群组的信息
		return center.getServer().admitInvitation(center.getLoginUser().getID(), gid);
	}

}
