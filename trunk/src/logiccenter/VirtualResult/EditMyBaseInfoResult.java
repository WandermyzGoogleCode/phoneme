package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.SimpleError;
import entity.UserInfo;

public class EditMyBaseInfoResult extends OneTimeVirtualResult {
	private BaseUserInfo baseInfo;

	public EditMyBaseInfoResult(BaseUserInfo baseInfo,
			LogicCenter center) {
		super(center);
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
		BoolInfo res = center.getServer().editMyBaseInfo(baseInfo);
		if (res.isTrue()){
			UserInfo newInfo = new UserInfo(baseInfo);
			newInfo.setCustomInfo(null);//不改变custom的字段
			center.getDataCenter().setUserInfo(newInfo);
			center.getAllContactsBox().editContact(newInfo);
		}
		return res;
	}
}
