package logiccenter;

import java.util.Observable;
import java.util.Date;

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
	private VirtualState state = VirtualState.LOADING;//当前Result获取的状态
	protected Error err = null;//如果当前状态为出错，那么这个记录了出错信息
	protected Date updateTime = null;//当前Result最后一次更新的时间，当Result不断更新的时候有用
	
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
