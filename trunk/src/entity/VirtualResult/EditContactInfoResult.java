package entity.VirtualResult;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.UserInfo;

public class EditContactInfoResult extends OneTimeVirtualResult {
	private UserInfo info;

	public EditContactInfoResult(UserInfo info, LogicCenter center) {
		super(center);
		this.info = info;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() {
		center.getDataCenter().setUserInfo(info);
		//TODO ��ǰû�д���dataCenter�Ĵ���
		return new BoolInfo();
	}
}
