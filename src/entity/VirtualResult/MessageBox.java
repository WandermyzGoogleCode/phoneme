package entity.VirtualResult;
import java.util.Calendar;
import java.util.List;

import logiccenter.LogicCenter;

import serverLogicCenter.ServerLogicCenter;

import entity.ID;
import entity.SimpleError;

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
		private LogicCenter center;
		private ID thisUser;

		@Override
		public void run() {
			try
			{
				//TODO IMPORTANT ��ζ�Զ�̵��õĺ���ǿ�н����������ڳ�ʱ��ʱ�򣬻������û�ָ����ʱ�� 
				//��ǰ�Ĳ����ǣ����û��˳���¼��ʱ�򣬷������Զ��Ͽ����RMI�����ӣ��Ӷ��ͷŸ��̡߳�
				messages = center.getServer().getAllMessages(thisUser);
				setPrepared();
				while (!isInterrupted())
				{
					Message newMessage = center.getServer().getNewMessage(thisUser);
					if (isInterrupted())
						break;
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
		
		public MessageRetriever(ID thisUser, LogicCenter center)
		{
			this.thisUser = thisUser;
			this.center = center;
		}
	}
	
	synchronized public List<Message> getMessages(){
		if (!getState().equals(VirtualState.PREPARED)){//������û��׼���õ�ʱ�������ȡ
			System.err.println("err: you can't getMessages when it's not PREPARED.");
			return null;
		}
		return messages;
	}
	
	synchronized public int getMessageCnt(){
		if (!getState().equals(VirtualState.PREPARED)){//������û��׼���õ�ʱ�������ȡ
			System.err.println("err: you can't getMessageCnt when it's not PREPARED.");
			return 0;
		}
		return messages.size();
	}
	
	/**
	 * ���뵱ǰ����Ϣ������thisUser������һ������
	 * ��Ϣ�ռ��䡣
	 * @param thisUser
	 */
	public MessageBox(ID thisUser, LogicCenter center)
	{
		//TODO ���Խ׶Σ�������������Ϊ������IDȫ��-1
		/*
		if (thisUser.getValue() == -1)
		{
			setError(new SimpleError("not login"));
			return;
		}*/
		retrieveThread =  new MessageRetriever(thisUser, center);
		retrieveThread.start();
	}
}
