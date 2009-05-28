package logiccenter.VirtualResult;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;

public class SearchUserResult extends OneTimeVirtualResult {
	private BaseUserInfo b;

	public SearchUserResult(BaseUserInfo b, LogicCenter center) {
		super(center);
		this.b = b;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws Exception {
		//TODO ÍêÉÆÓÃ»§ËÑË÷
		return new BoolInfo();
	}

}
