package entity.VirtualResult;

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
		//TODO ��ǰû�д���dataCenter�Ĵ���
		return new BoolInfo();
	}

}
