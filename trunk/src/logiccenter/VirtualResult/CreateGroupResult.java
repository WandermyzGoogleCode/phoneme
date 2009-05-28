package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.Permission;
import entity.SimpleError;

public class CreateGroupResult extends OneTimeVirtualResult {
	private Group g;
	private Permission p;

	public CreateGroupResult(Group g, Permission p,
			LogicCenter center) {
		super(center);
		this.g = g;
		this.p = p;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO 如果成功，更新相关群组，以及用户所创建的群组的信息
		return center.getServer().createGroup(center.getLoginUser().getID(), g, p);
	}

}
