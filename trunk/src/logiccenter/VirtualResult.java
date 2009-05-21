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
	protected VirtualState state;//当前Result获取的状态
	protected Error err;//如果当前状态为出错，那么这个记录了出错信息
	protected Date updateTime;//当前Result最后一次更新的时间，当Result不断更新的时候有用
	
	public VirtualState getState(){
		return state;
	}
	
	public Error getError(){
		return err;
	}
	
	public Date getUpdateTime(){
		return updateTime;
	}
}
