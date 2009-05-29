package entity.message;

import java.io.Serializable;

import entity.ID;

import logiccenter.LogicCenter;

/**
 * 所有服务器给客户端主动发送的东西都继承自这个接口，
 * 比如各种通知、邀请、申请等。
 * @author Administrator
 *
 */
public abstract class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4303514006910661575L;
	private ID id;
	
	public ID getID(){
		return id;
	}
	
	public Message(ID id){
		this.id = id;
	}

	public void remove(LogicCenter center) {
		center.removeMessage(this);
	}

	public abstract void proceed(LogicCenter center);
	public abstract String title();
	public abstract String detail();
	/**
	 * 该消息是否应该自动proceed
	 * 如果应该自动proceed，那么请UI在获得该消息以后，
	 * 自动调用proceed()，而不是等用户来触发。
	 * 同时，告知用户有这样一个消息来了（不用询问要删除或是要proceed）
	 * 告知完毕以后，将该信息自动删除
	 * @return
	 */
	public abstract boolean autoProceed();
	/**
	 * 该消息是否已经proceed过
	 * @return
	 */
	public abstract boolean proceeded();
}
