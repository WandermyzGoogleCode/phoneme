package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Password;
import entity.SimpleError;
import entity.UserInfo;

public class EditMyBaseInfoResult extends OneTimeVirtualResult {
	private BaseUserInfo baseInfo;
	private Password pwd;

	public EditMyBaseInfoResult(BaseUserInfo baseInfo, Password pwd,
			LogicCenter center) {
		super(center);
		this.pwd = pwd;
		this.baseInfo = baseInfo;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else if (!center.getLoginUser().getID().equals(baseInfo.getID()))
			setError(ErrorType.ID_NOT_MATCHED);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		BoolInfo res = center.getServer().editMyBaseInfo(baseInfo, pwd);
		if (res.isTrue())
			center.editLoginUser(baseInfo);
		return res;
	}
}
