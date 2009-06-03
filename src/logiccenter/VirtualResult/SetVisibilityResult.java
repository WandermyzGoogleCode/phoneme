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
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		BoolInfo res = center.getServer().setVisibility(center.getLoginUser().getID(), targetID, visibility);
		return res;
	}

}
