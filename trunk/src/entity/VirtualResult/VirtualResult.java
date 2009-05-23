package entity.VirtualResult;

import java.util.Calendar;
import java.util.Observable;
import java.util.Date;
import entity.MyError;
import static entity.VirtualResult.VirtualState.*;

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
	private VirtualState state = LOADING;//��ǰResult��ȡ��״̬
	protected MyError err = null;//�����ǰ״̬Ϊ������ô�����¼�˳�����Ϣ
	protected Date updateTime = null;//��ǰResult���һ�θ��µ�ʱ�䣬��Result���ϸ��µ�ʱ������
	
	public VirtualState getState(){
		return state;
	}
	
	public MyError getError(){
		return err;
	}
	
	synchronized public Date getUpdateTime(){
		return updateTime;
	}
	
	synchronized protected void setPrepared()
	{
		state = PREPARED;
		notifyObservers();
	}
	
	synchronized protected void setUpdateTime(Date time)
	{
		this.state = PREPARED;
		updateTime = time;
		setChanged();
		notifyObservers();
	}
	
	synchronized protected void setUpdateNow(){
		this.state = PREPARED;
		updateTime = Calendar.getInstance().getTime();
		setChanged();
		notifyObservers();		
	}
	
	synchronized protected void setError(MyError err)
	{
		this.err = err;
		this.state = ERRORED;
		notifyObservers();
	}	
}
