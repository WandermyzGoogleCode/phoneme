package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;

public class RemoveSynContactResult extends OneTimeVirtualResult {
	private ID targetID;

	public RemoveSynContactResult(ID targetID, LogicCenter center) {
		super(center);
		this.targetID = targetID;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		BoolInfo res = center.getServer().removeSynContact(center.getLoginUser().getID(), targetID);
		if (res.isTrue())
			center.getDataCenter().removeSynRelationship(targetID);
		return res;
	}
}
