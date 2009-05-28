package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.MyRemoteException;
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
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		BaseUserInfo loginUser = center.getServer().login(identicalInfo, pwd);
		center.setLoginUser(loginUser);
		return new BoolInfo();
	}
}
