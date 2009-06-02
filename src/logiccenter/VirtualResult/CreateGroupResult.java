package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.MyRemoteException;
import entity.Permission;
import entity.SimpleError;

public class CreateGroupResult extends OneTimeVirtualResult {
	private Group g;
	private Permission p;
	int visibility;

	public CreateGroupResult(Group g, Permission p, int visibility,
			LogicCenter center) {
		super(center);
		this.visibility = visibility;
		this.g = g;
		this.p = p;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		Group resG = center.getServer().createGroup(center.getLoginUser().getID(), g, p, visibility);
		center.getDataCenter().setGroup(resG);
		center.getAllGroupsBox().editGroup(resG);
		return new BoolInfo();
	}

}
