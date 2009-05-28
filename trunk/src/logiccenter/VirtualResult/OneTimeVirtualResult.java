package logiccenter.VirtualResult;

import static logiccenter.VirtualResult.VirtualState.*;

import logiccenter.LogicCenter;

import entity.BoolInfo;
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
	protected abstract BoolInfo getResult() throws Exception;
	protected LogicCenter center;

	class GetThread extends Thread
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
			catch (Exception e)
			{
				setError(null);
				System.err.println("Exception: "+e.toString());
				e.printStackTrace();
				//TODO 写入相应的错误到error中
			}
		}
	}
	
	protected GetThread thread;
	
	protected OneTimeVirtualResult(LogicCenter center)
	{
		this.center = center;
		thread = new GetThread();
	}
	
	/**
	 * 检查当前LogicCenter的登录用户是否为空
	 * @return
	 */
	protected boolean noLoginUser(){
		return (center.getLoginUser().isNull());
	}
}
