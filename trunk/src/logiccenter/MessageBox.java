package logiccenter;
import java.util.ArrayList;
import java.util.List;

import entity.ID;

import entity.Message;

/**
 * 一个给出当前有哪些消息的VirtualResult。
 * 消息包括：有人申请成为被授权联系人、群组邀请、群组申请等等。
 * 该类可以获得当前有多少未处理的信息，以及相应的信息。
 * 
 * 其它实现Virtual Proxy设计模式的类和该类类似。
 * @author Administrator
 *
 */
public class MessageBox extends VirtualResult {
	private ArrayList<Message> messages;
	
	/**
	 * 单独获取新信息的线程Task
	 * @author Administrator
	 *
	 */
	class MessageRetriever implements Runnable
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*
			 * 如果网络部分实现好了，代码大致如下：
			 * getAllMessagesFromServer();
			 * setState(PREPARED);
			 * notifyObservers();
			 * while (CONDITION)
			 * {
			 * 	result = getNewMassagesFromServer();
			 * 	setUpdateTime(NOW);
			 * 	notifyObservers(); 
			 * }
			 */
		}
	}
	
	synchronized public List<Message> getMessages(){
		return messages;
	}
	
	synchronized public int getMessageCnt(){
		return messages.size();
	}
	
	/**
	 * 传入当前的信息接收人thisUser，构造一个他的
	 * 信息收件箱。
	 * @param thisUser
	 */
	public MessageBox(ID thisUser)
	{
		state = VirtualState.LOADING;
		Thread retrieveThread =  new Thread(new MessageRetriever());
		retrieveThread.start();
		//TODO some other works...
	}
}
