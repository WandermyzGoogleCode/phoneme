package logiccenter.VirtualResult;

import static logiccenter.VirtualResult.VirtualState.*;

import logiccenter.LogicCenter;

import entity.BoolInfo;
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
				//TODO д����Ӧ�Ĵ���error��
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
	 * ��鵱ǰLogicCenter�ĵ�¼�û��Ƿ�Ϊ��
	 * @return
	 */
	protected boolean noLoginUser(){
		return (center.getLoginUser().isNull());
	}
}
