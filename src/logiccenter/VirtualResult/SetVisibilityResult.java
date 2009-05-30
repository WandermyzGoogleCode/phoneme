package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;

public class SetVisibilityResult extends OneTimeVirtualResult {
	private ID targetID;
	private int visibility;

	public SetVisibilityResult(ID targetID, int visibility,
			LogicCenter center) {
		super(center);
		this.visibility = visibility;
		this.targetID = targetID;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		BoolInfo res = center.getServer().setVisibility(center.getLoginUser().getID(), targetID, visibility);
		if (res.isTrue())
			center.getDataCenter().setVisibility(targetID, visibility);
		return res;
	}

}
