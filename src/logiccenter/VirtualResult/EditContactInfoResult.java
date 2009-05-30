package logiccenter.VirtualResult;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.SimpleError;
import entity.UserInfo;

public class EditContactInfoResult extends OneTimeVirtualResult {
	private UserInfo info;

	public EditContactInfoResult(UserInfo info, LogicCenter center) {
		super(center);
		this.info = info;
		if (info.getBaseInfo().isNull())
			setError(ErrorType.EDIT_NULL_USER);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() {
		center.getDataCenter().setUserInfo(info);
		center.getAllContactsBox().editContact(info);
		return new BoolInfo();
	}
}
