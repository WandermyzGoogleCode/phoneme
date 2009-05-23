package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import entity.BoolInfo;
import entity.Password;
import entity.infoField.IdenticalInfoField;

public class LoginResult extends OneTimeVirtualResult {
	private IdenticalInfoField identicalInfo;
	private Password pwd;

	public LoginResult(IdenticalInfoField identicalInfo, Password pwd,
			LogicCenter center) {
		super(center);
		this.identicalInfo = identicalInfo;
		this.pwd = pwd;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		// TODO Auto-generated method stub
		// TODO IMPORTANT ÍêÉÆµÇÂ¼ÏµÍ³
		return new BoolInfo();
	}
}
