package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.Password;

public class RegisterResult extends OneTimeVirtualResult {
	private BaseUserInfo b;
	private Password pwd;

	public RegisterResult(BaseUserInfo b, Password pwd,
			LogicCenter center) {
		super(center);
		//TODO 检测b的是否满足条件
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().register(b, pwd);
	}

}
