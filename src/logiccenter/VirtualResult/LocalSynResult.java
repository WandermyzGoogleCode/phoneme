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
		//������Զ��ͬ��
		boolean doRemoteSyn = center.getUI().yesOrNo("�������ڱ���ͬ��ǰ�Ƚ���Զ��ͬ������֤���ݵ���Ч�ԡ�����Ҫ����Զ��ͬ����");
		if (doRemoteSyn){
			intermediateInfo = "����Զ��ͬ��...";
			setChanged();
			notifyObservers();
			RemoteSynResult remoteSynRes = center.remoteSynchronize();
			try {
				remoteSynRes.waitForComplete();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return new BoolInfo("Զ��ͬ�����������ж�");
			} catch (ExecutionException e) {
				e.printStackTrace();
				return new BoolInfo("Զ��ͬ������"+e.toString());
			}
			intermediateInfo = "Զ��ͬ�����";
			setChanged();
			notifyObservers();
		}
		
		return new BoolInfo();
	}

}
