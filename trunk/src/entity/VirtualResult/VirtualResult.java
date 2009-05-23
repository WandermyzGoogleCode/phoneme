package entity.VirtualResult;

import java.util.Calendar;
import java.util.Observable;
import java.util.Date;
import entity.MyError;
import static entity.VirtualResult.VirtualState.*;

/**
 * 提供给GUI的带VirtualProxy的结果的最高层祖先。继承了
 * Observable从而能够让GUI监视该结果的最新状态。
 * 通过state向GUI通知当前的状态，对于不断更新的结果，通过
 * updateTime来通知GUI是否需要更新。
 * 
 * 改类的子类会有许多get的函数以获取各种实际需要的结果。这些
 * 资源通常由子类自带的一个额外的线程来获取。
 * 
 * 当前可以看{@link MessageBox}来获得一个子类的Example。
 * @author Administrator
 *
 */
public abstract class VirtualResult extends Observable {
	private VirtualState state = LOADING;//当前Result获取的状态
	protected MyError err = null;//如果当前状态为出错，那么这个记录了出错信息
	protected Date updateTime = null;//当前Result最后一次更新的时间，当Result不断更新的时候有用
	
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
