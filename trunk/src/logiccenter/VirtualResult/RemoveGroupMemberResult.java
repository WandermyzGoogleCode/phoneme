package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.MyRemoteException;
import entity.infoField.IdenticalInfoField;

public class RemoveGroupMemberResult extends OneTimeVirtualResult {
	private IdenticalInfoField un;
	private Group g;

	public RemoveGroupMemberResult(IdenticalInfoField un, Group g,
			LogicCenter center) {
		super(center);
		this.un = un;
		this.g = g;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else if (!center.getLoginUser().getID().equals(g.getAdminUserID()))
			setError(ErrorType.NOT_ADMIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		Group newG = center.getServer().removeGroupMember(center.getLoginUser().getID(), un, g.getID());
		center.getDataCenter().setGroup(newG);
		center.getAllGroupsBox().editGroup(newG);
		return new BoolInfo();
	}

}
