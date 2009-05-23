package logiccenter;
import java.util.Calendar;
import java.util.List;

import serverLogicCenter.ServerLogicCenter;

import entity.ID;

import entity.message.Message;

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
	private List<Message> messages;
	private Thread retrieveThread;
	
	/**
	 * ������ȡ����Ϣ���߳�Task
	 * @author Administrator
	 *
	 */
	
	class MessageRetriever extends Thread
	{
		private ServerLogicCenter server;
		private ID thisUser;

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
			try
			{
				//TODO IMPORTANT ��ζ�Զ�̵��õĺ���ǿ�н����������ڳ�ʱ��ʱ�򣬻������û�ָ����ʱ�� 
				messages = server.getAllMessages(thisUser);
				setState(VirtualState.PREPARED);
				while (!isInterrupted())
				{
					Message newMessage = server.getNewMessage(thisUser);
					messages.add(newMessage);
					setUpdateTime(Calendar.getInstance().getTime());
				}
			}
			catch (Exception e)
			{
				System.err.println("Exception: "+e.toString());
				e.printStackTrace();
			}
		}
		
		public MessageRetriever(ID thisUser, ServerLogicCenter server)
		{
			this.thisUser = thisUser;
			this.server = server;
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
	public MessageBox(ID thisUser, ServerLogicCenter server)
	{
		retrieveThread =  new MessageRetriever(thisUser, server);
		retrieveThread.start();
		//TODO some other works...
	}
	
	/*obsolete
	@Override
	public synchronized void terminate() {
		System.err.println(this.getClass().toString()+" terminated");
		retrieveThread.interrupt();
	}*/
}
