package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.Permission;
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
	private Permission p;
	private int visibility;

	public AdmitInvitationResult(ID gid, Permission p, int visibility, LogicCenter center) {
		super(center);
		this.gid = gid;
		this.p = p;
		this.visibility = visibility;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().admitInvitation(center.getLoginUser().getID(), gid, p, visibility);
	}

}
