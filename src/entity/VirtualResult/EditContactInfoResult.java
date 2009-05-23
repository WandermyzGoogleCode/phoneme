package entity.VirtualResult;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.SimpleError;
import entity.UserInfo;

public class EditContactInfoResult extends OneTimeVirtualResult {
	private UserInfo info;

	public EditContactInfoResult(UserInfo info, LogicCenter center) {
		super(center);
		this.info = info;
		if (info.getBaseInfo().isNull())
			setError(new SimpleError("null user can't be edited"));
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() {
		center.getDataCenter().setUserInfo(info);
		//TODO 当前没有处理dataCenter的错误
		return new BoolInfo();
	}
}
