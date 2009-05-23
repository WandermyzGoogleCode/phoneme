package entity.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ID;
import entity.SimpleError;

public class SetVisibilityResult extends OneTimeVirtualResult {
	private ID targetID;
	private int visibility;

	public SetVisibilityResult(ID targetID, int visibility,
			LogicCenter center) {
		super(center);
		this.visibility = visibility;
		this.targetID = targetID;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else
			thread.start();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BoolInfo getResult() throws Exception {
		BoolInfo res = center.getServer().setVisibility(center.getLoginUser().getID(), targetID, visibility);
		if (res.isTrue())
			center.getDataCenter().setVisibility(targetID, visibility);
		//TODO û�д�����ܵ�dataCenter�Ĵ���
		return res;
	}

}
