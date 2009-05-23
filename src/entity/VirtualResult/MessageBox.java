package entity.VirtualResult;
import java.util.Calendar;
import java.util.List;

import logiccenter.LogicCenter;

import serverLogicCenter.ServerLogicCenter;

import entity.ID;
import entity.SimpleError;

import entity.message.Message;

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
	private List<Message> messages;
	private Thread retrieveThread;
	
	/**
	 * 单独获取新信息的线程Task
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
				//TODO IMPORTANT 如何对远程调用的函数强行结束，比如在超时的时候，或者在用户指定的时候？ 
				//当前的策略是，当用户退出登录的时候，服务器自动断开相关RMI的连接，从而释放该线程。
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
		if (!getState().equals(VirtualState.PREPARED)){//保护在没有准备好的时候就来索取
			System.err.println("err: you can't getMessages when it's not PREPARED.");
			return null;
		}
		return messages;
	}
	
	synchronized public int getMessageCnt(){
		if (!getState().equals(VirtualState.PREPARED)){//保护在没有准备好的时候就来索取
			System.err.println("err: you can't getMessageCnt when it's not PREPARED.");
			return 0;
		}
		return messages.size();
	}
	
	/**
	 * 传入当前的信息接收人thisUser，构造一个他的
	 * 信息收件箱。
	 * @param thisUser
	 */
	public MessageBox(ID thisUser, LogicCenter center)
	{
		//TODO 测试阶段，不做错误处理，因为进来的ID全是-1
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
