package logiccenter.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.StatResult;

public class GetStatResultResult extends OneTimeVirtualResult {
	private StatResult statResult;

	public GetStatResultResult(LogicCenter center) {
		super(center);
		center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult(){
		statResult = center.calcStat();
		return new BoolInfo();
	}

	public StatResult getStatResult(){
		if (getState() != VirtualState.PREPARED){//������û��׼���õ�ʱ�������ȡ
			System.err.println("err: you can't getStatResult when it's not PREPARED.");
			return null;
		}
		return statResult;
	}
}
