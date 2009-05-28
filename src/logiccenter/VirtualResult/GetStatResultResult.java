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
		//TODO û�д�����ܵĴ���
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
