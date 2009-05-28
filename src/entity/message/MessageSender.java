package entity.message;

import java.util.ArrayDeque;
import java.util.Queue;

import entity.ID;

public class MessageSender {
	private ID user;
	private Queue<Message> queue;
	private boolean alive = true;
	
	public MessageSender(ID user){
		this.user = user;
		queue = new ArrayDeque<Message>();
	}
	
	public ID getUser(){
		return user;
	}
	
	synchronized public boolean hasMessage(){
		return !queue.isEmpty();
	}
	
	synchronized public Message getMessage(){
		return queue.poll();
	}
	
	synchronized public void addMessage(Message msg){
		queue.add(msg);
		notifyAll();
	}
	
	/**
	 * ɾ����֮ǰ���ȵ��øú������ü��Ӹö���Ĺ����˳�
	 */
	synchronized public void close(){
		alive = false;
	}
	
	synchronized public boolean isAlive(){
		return alive;
	}
}