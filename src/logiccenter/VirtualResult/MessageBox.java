package logiccenter.VirtualResult;

import java.util.Calendar;
import java.util.List;

import logiccenter.LogicCenter;

import entity.BaseUserInfo;
import entity.ID;
import entity.MyRemoteException;
import entity.SimpleError;

import entity.message.Message;

/**
 * 一个给出当前有哪些消息的VirtualResult。 消息包括：有人申请成为被授权联系人、群组邀请、群组申请等等。
 * 该类可以获得当前有多少未处理的信息，以及相应的信息。
 * 
 * 其它实现Virtual Proxy设计模式的类和该类类似。
 * 
 * @author Administrator
 * 
 */
public class MessageBox extends VirtualResult {
	private List<Message> messages;
	private Thread retrieveThread;

	/**
	 * 单独获取新信息的线程Task
	 * 
	 * @author Administrator
	 * 
	 */

	class MessageRetriever extends Thread {
		private LogicCenter center;
		private ID thisUser;

		@Override
		public void run() {
			try {
				messages = center.getServer().getAllMessages(thisUser);
				setPrepared();
				while (!isInterrupted()) {
					Message newMessage = center.getServer().getNewMessage(
							thisUser);
					System.out.println(center.getLoginUser().getName()
							+ Messages.getString("MessageBox.0"));// TODO TEST //$NON-NLS-1$
					if (isInterrupted())
						break;
					if (newMessage == null) {
						setError(new SimpleError(Messages.getString("MessageBox.1"))); //$NON-NLS-1$
						center.setLoginUser(new BaseUserInfo());//将当前登录用户设为无效
						center.logout();
						break;
					}
					messages.add(newMessage);
					System.out.println(Messages.getString("MessageBox.2") + messages.size());// TODO TEST //$NON-NLS-1$
					setUpdateNow();
				}
			} catch (MyRemoteException e) {
				setError(e.getErr());
			} catch (Exception e) {
				System.err.println(Messages.getString("MessageBox.3") + e.toString()); //$NON-NLS-1$
				e.printStackTrace();
			}
		}

		public MessageRetriever(ID thisUser, LogicCenter center) {
			this.thisUser = thisUser;
			this.center = center;
		}
	}

	public List<Message> getMessages() {
		if (getState() != VirtualState.PREPARED) {// 保护在没有准备好的时候就来索取
			System.err
					.println(Messages.getString("MessageBox.4")); //$NON-NLS-1$
			return null;
		}
		return messages;
	}

	public int getMessageCnt() {
		if (getState() != VirtualState.PREPARED) {// 保护在没有准备好的时候就来索取
			System.err
					.println(Messages.getString("MessageBox.5")); //$NON-NLS-1$
			return 0;
		}
		return messages.size();
	}

	/**
	 * 传入当前的信息接收人thisUser，构造一个他的 信息收件箱。
	 * 
	 * @param thisUser
	 */
	public MessageBox(ID thisUser, LogicCenter center) {
		retrieveThread = new MessageRetriever(thisUser, center);
		retrieveThread.start();
	}

	/**
	 * 当MessageBox不再使用，要调用该函数来关闭获取线程。
	 */
	public void close() {
		retrieveThread.interrupt();
	}

	public void removeMessage(Message msg) {
		for (int i = 0; i < messages.size(); i++)
			if (messages.get(i).getID().equals(msg.getID())) {
				messages.remove(i);
				break;
			}
		setUpdateNow();
	}

	public void refresh() {
		setUpdateNow();
	}
}
