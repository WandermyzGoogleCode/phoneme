package logiccenter;

import java.util.Observable;
import java.util.Date;

/**
 * �ṩ��GUI�Ĵ�VirtualProxy�Ľ������߲����ȡ��̳���
 * Observable�Ӷ��ܹ���GUI���Ӹý��������״̬��
 * ͨ��state��GUI֪ͨ��ǰ��״̬�����ڲ��ϸ��µĽ����ͨ��
 * updateTime��֪ͨGUI�Ƿ���Ҫ���¡�
 * 
 * ���������������get�ĺ����Ի�ȡ����ʵ����Ҫ�Ľ������Щ
 * ��Դͨ���������Դ���һ��������߳�����ȡ��
 * 
 * ��ǰ���Կ�{@link MessageBox}�����һ�������Example��
 * @author Administrator
 *
 */
public abstract class VirtualResult extends Observable {
	private VirtualState state = VirtualState.LOADING;//��ǰResult��ȡ��״̬
	protected Error err = null;//�����ǰ״̬Ϊ������ô�����¼�˳�����Ϣ
	protected Date updateTime = null;//��ǰResult���һ�θ��µ�ʱ�䣬��Result���ϸ��µ�ʱ������
	
	public VirtualState getState(){
		return state;
	}
	
	public Error getError(){
		return err;
	}
	
	synchronized public Date getUpdateTime(){
		return updateTime;
	}
	
	synchronized protected void setState(VirtualState newState)
	{
		state = newState;
		notifyObservers();
	}
	
	synchronized protected void setUpdateTime(Date time)
	{
		this.state = VirtualState.PREPARED;
		updateTime = time;
		setChanged();
		notifyObservers();
	}
	
	synchronized protected void setError(Error err)
	{
		this.err = err;
		this.state = VirtualState.ERRORED;
		notifyObservers();
	}	
}
