package logiccenter;
import java.util.ArrayList;
import java.util.List;

import entity.ID;

import entity.Message;

/**
 * һ��������ǰ����Щ��Ϣ��VirtualResult��
 * ��Ϣ���������������Ϊ����Ȩ��ϵ�ˡ�Ⱥ�����롢Ⱥ������ȵȡ�
 * ������Ի�õ�ǰ�ж���δ�������Ϣ���Լ���Ӧ����Ϣ��
 * 
 * ����ʵ��Virtual Proxy���ģʽ����͸������ơ�
 * @author Administrator
 *
 */
public class MessageBox extends VirtualResult {
	private ArrayList<Message> messages;
	
	/**
	 * ������ȡ����Ϣ���߳�Task
	 * @author Administrator
	 *
	 */
	class MessageRetriever implements Runnable
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*
			 * ������粿��ʵ�ֺ��ˣ�����������£�
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
	 * ���뵱ǰ����Ϣ������thisUser������һ������
	 * ��Ϣ�ռ��䡣
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
