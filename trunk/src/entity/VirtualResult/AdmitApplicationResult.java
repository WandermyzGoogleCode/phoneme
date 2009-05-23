package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ID;
import entity.SimpleError;

/**
 * 状态为PREPARED就表示已经成功操作
 * ERRORED表示操作出错
 * LOADING表示正在操作
 * @author Administrator
 *
 */
public class AdmitApplicationResult extends OneTimeVirtualResult {
	private ID gid, uid;
	
	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO 如果成功，应该更新相应的群组信息。
		return center.getServer().admitApplication(center.getLoginUser().getID(), gid, uid);
	}

	public AdmitApplicationResult(ID gid, ID uid, LogicCenter center){
		super(center);
		this.gid = gid;
		this.uid = uid;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else
			thread.start();
	}
}
