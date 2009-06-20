package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		BoolInfo res = center.getServer().removeSynContact(center.getLoginUser().getID(), targetID);
		if (res.isTrue()){
			center.getDataCenter().removeSynRelationship(targetID);
			List<ID> idList = new ArrayList<ID>();
			idList.add(targetID);
			center.getAllContactsBox().updateRelation(idList);
		}
		return res;
	}
}
