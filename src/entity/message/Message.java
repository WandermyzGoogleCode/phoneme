package entity.message;

import java.io.Serializable;

import entity.ID;

import logiccenter.LogicCenter;

/**
 * ���з��������ͻ����������͵Ķ������̳�������ӿڣ�
 * �������֪ͨ�����롢����ȡ�
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

	public void ignore(LogicCenter center) {
		center.ignoreMessage(this);
	}

	public abstract void proceed(LogicCenter center);
	public abstract String title();
	public abstract String detail();
}
