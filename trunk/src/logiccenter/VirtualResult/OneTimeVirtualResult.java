package logiccenter.VirtualResult;

import static logiccenter.VirtualResult.VirtualState.*;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.MyRemoteException;
import entity.SimpleError;

/**
 * 单次读取的VirtualResult，稍稍用了一点Template Design Pattern，
 * 将单次读取的method给abstract了一下，以后子类继承不需要自己开线程，
 * 只需要实现读取的method：getResult即可。
 * 
 * 子类必须在自己初始化以后，自己启动thread.start()
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
	 * 检查当前LogicCenter的登录用户是否为空
	 * @return
	 */
	protected boolean noLoginUser(){
		return (center.getLoginUser() != null && center.getLoginUser().isNull());
	}
}
