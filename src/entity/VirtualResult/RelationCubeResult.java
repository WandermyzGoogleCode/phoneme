package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.SimpleError;
import entity.infoField.IdenticalInfoField;

public class RelationCubeResult extends OneTimeVirtualResult {
	private IdenticalInfoField from, to;

	public RelationCubeResult(IdenticalInfoField from, IdenticalInfoField to,
			LogicCenter center) {
		super(center);
		this.from = from;
		this.to = to;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO 完善人立方搜索
		return new BoolInfo();
	}
}
