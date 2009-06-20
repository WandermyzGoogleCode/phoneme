package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.MyRemoteException;

public class LocalSynResult extends OneTimeVirtualResult {
	public LocalSynResult(LogicCenter center) {
		super(center);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
