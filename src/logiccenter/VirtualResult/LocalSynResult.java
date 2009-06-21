package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.MyRemoteException;

public class LocalSynResult extends OneTimeVirtualResult {
	public LocalSynResult(LogicCenter center) {
		super(center);
		center.getExecutor().submit(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		//建议先远程同步
		boolean doRemoteSyn = center.getUI().yesOrNo("建议您在本地同步前先进行远程同步，保证数据的有效性。请问要进行远程同步吗？");
		if (doRemoteSyn){
			intermediateInfo = "正在远程同步...";
			setChanged();
			notifyObservers();
			RemoteSynResult remoteSynRes = center.remoteSynchronize();
			try {
				remoteSynRes.waitForComplete();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return new BoolInfo("远程同步出错：任务被中断");
			} catch (ExecutionException e) {
				e.printStackTrace();
				return new BoolInfo("远程同步出错："+e.toString());
			}
			intermediateInfo = "远程同步完成";
			setChanged();
			notifyObservers();
		}
		
		return new BoolInfo();
	}

}
