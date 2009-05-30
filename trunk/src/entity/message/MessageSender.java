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
	 * 删除其之前，先调用该函数，让监视该对象的过程退出
	 */
	synchronized public void close(){
		alive = false;
		notifyAll();
	}
	
	synchronized public boolean isAlive(){
		return alive;
	}
}
