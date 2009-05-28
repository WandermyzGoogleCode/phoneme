package logiccenter.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.StatResult;

public class GetStatResultResult extends OneTimeVirtualResult {
	private StatResult statResult;

	public GetStatResultResult(LogicCenter center) {
		super(center);
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws Exception {
		statResult = center.calcStat();
		//TODO 没有处理可能的错误
		return new BoolInfo();
	}

	public StatResult getStatResult(){
		if (getState() != VirtualState.PREPARED){//保护在没有准备好的时候就来索取
			System.err.println("err: you can't getStatResult when it's not PREPARED.");
			return null;
		}
		return statResult;
	}
}
