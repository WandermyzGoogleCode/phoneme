package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.SimpleError;

public class EditGroupResult extends OneTimeVirtualResult {
	private Group g;

	public EditGroupResult(Group g, LogicCenter center) {
		super(center);
		this.g = g;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		BoolInfo res = center.getServer().editGroup(center.getLoginUser().getID(), g);
		if (res.isTrue()){
			center.getDataCenter().setGroup(g);
			center.getAllGroupsBox().editGroup(g);
		}
		return res;
	}

}
