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
		if (getState() != VirtualState.PREPARED){//保护在没有准备好的时候就来索取
			System.err.println(Messages.getString("GetStatResultResult.0")); //$NON-NLS-1$
			return null;
		}
		return statResult;
	}
}
