package logiccenter.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.SimpleError;

public class RemovePerContactResult extends OneTimeVirtualResult {
	private ID targetID;

	public RemovePerContactResult(ID targetID, LogicCenter center) {
		super(center);
		this.targetID = targetID;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws Exception {
		BoolInfo res = center.getServer().removePerContact(center.getLoginUser().getID(), targetID);
		if (res.isTrue())
			center.getDataCenter().removePerRelationship(targetID);
		//TODO ��ǰû�д���dataCenter���ܳ��ֵĴ���
		return res;
	}
}
