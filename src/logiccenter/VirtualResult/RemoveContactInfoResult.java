package logiccenter.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ID;

public class RemoveContactInfoResult extends OneTimeVirtualResult {
	private ID id;

	public RemoveContactInfoResult(ID id, LogicCenter center) {
		super(center);
		this.id = id;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() {
		center.getDataCenter().removeUserInfo(id);
		center.getAllContactsBox().removeContact(id);
		//TODO 当前没有处理dataCenter的错误
		return new BoolInfo();
	}

}
