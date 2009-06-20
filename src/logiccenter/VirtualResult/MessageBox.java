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
 * һ��������ǰ����Щ��Ϣ��VirtualResult�� ��Ϣ���������������Ϊ����Ȩ��ϵ�ˡ�Ⱥ�����롢Ⱥ������ȵȡ�
 * ������Ի�õ�ǰ�ж���δ�������Ϣ���Լ���Ӧ����Ϣ��
 * 
 * ����ʵ��Virtual Proxy���ģʽ����͸������ơ�
 * 
 * @author Administrator
 * 
 */
public class MessageBox extends VirtualResult {
	private List<Message> messages;
	private Thread retrieveThread;

	/**
	 * ������ȡ����Ϣ���߳�Task
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
						center.setLoginUser(new BaseUserInfo());//����ǰ��¼�û���Ϊ��Ч
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
		if (getState() != VirtualState.PREPARED) {// ������û��׼���õ�ʱ�������ȡ
			System.err
					.println(Messages.getString("MessageBox.4")); //$NON-NLS-1$
			return null;
		}
		return messages;
	}

	public int getMessageCnt() {
		if (getState() != VirtualState.PREPARED) {// ������û��׼���õ�ʱ�������ȡ
			System.err
					.println(Messages.getString("MessageBox.5")); //$NON-NLS-1$
			return 0;
		}
		return messages.size();
	}

	/**
	 * ���뵱ǰ����Ϣ������thisUser������һ������ ��Ϣ�ռ��䡣
	 * 
	 * @param thisUser
	 */
	public MessageBox(ID thisUser, LogicCenter center) {
		retrieveThread = new MessageRetriever(thisUser, center);
		retrieveThread.start();
	}

	/**
	 * ��MessageBox����ʹ�ã�Ҫ���øú������رջ�ȡ�̡߳�
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
