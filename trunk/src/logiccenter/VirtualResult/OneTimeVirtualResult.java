package logiccenter.VirtualResult;

import static logiccenter.VirtualResult.VirtualState.*;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.MyRemoteException;
import entity.SimpleError;

/**
 * ���ζ�ȡ��VirtualResult����������һ��Template Design Pattern��
 * �����ζ�ȡ��method��abstract��һ�£��Ժ�����̳в���Ҫ�Լ����̣߳�
 * ֻ��Ҫʵ�ֶ�ȡ��method��getResult���ɡ�
 * 
 * ����������Լ���ʼ���Ժ��Լ�����thread.start()
 * @author Administrator
 *
 */
public abstract class OneTimeVirtualResult extends VirtualResult {
	protected abstract BoolInfo getResult() throws RemoteException, MyRemoteException;
	protected LogicCenter center;

	class GetTask implements Runnable
	{
		@Override
		public void run() {
			if (OneTimeVirtualResult.this.getState() != LOADING)
				return;
			try
			{
				BoolInfo boolInfo = getResult(); 
				if (boolInfo.isTrue())
					setPrepared();
				else
					setError(new SimpleError(boolInfo.getInfo()));
			}
			catch (MyRemoteException e){
				setError(e.getErr());
			}
			catch (RemoteException e)
			{
				setError(ErrorType.REMOTE_ERROR);
				System.err.println("Exception: "+e.toString());
				e.printStackTrace();
			}
		}
	}
	
	protected GetTask task;
	
	protected OneTimeVirtualResult(LogicCenter center)
	{
		this.center = center;
		task = new GetTask();
	}
	
	/**
	 * ��鵱ǰLogicCenter�ĵ�¼�û��Ƿ�Ϊ��
	 * @return
	 */
	protected boolean noLoginUser(){
		return (center.getLoginUser() != null && center.getLoginUser().isNull());
	}
}
