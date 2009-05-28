package logiccenter.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.SimpleError;

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
	protected BoolInfo getResult() throws Exception {
		BoolInfo res = center.getServer().removeSynContact(center.getLoginUser().getID(), targetID);
		if (res.isTrue())
			center.getDataCenter().removeSynRelationship(targetID);
		//TODO 当前没有处理dataCenter可能出现的错误
		return res;
	}
}
