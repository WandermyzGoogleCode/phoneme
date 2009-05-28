package logiccenter.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.Group;

public class SearchGroupResult extends OneTimeVirtualResult {
	private Group g;

	public SearchGroupResult(Group g, LogicCenter center) {
		super(center);
		this.g = g;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws Exception {
		//TODO ÍêÉÆÈº×éËÑË÷
		return new BoolInfo();
	}

}
