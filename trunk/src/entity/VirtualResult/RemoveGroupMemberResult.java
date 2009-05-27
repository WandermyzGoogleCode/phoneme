package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.SimpleError;
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
			setError(ErrorType.ID_NOT_MATCHED);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO 成功以后，更新相关群组信息
		return center.getServer().removeGroupMember(center.getLoginUser().getID(), un, g);
	}

}
