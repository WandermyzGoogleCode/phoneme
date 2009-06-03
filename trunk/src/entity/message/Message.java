package entity.message;

import java.io.Serializable;
import java.rmi.RemoteException;

import entity.ID;
import entity.MyRemoteException;

import logiccenter.LogicCenter;

/**
 * ���з��������ͻ����������͵Ķ������̳�������ӿڣ� �������֪ͨ�����롢����ȡ�
 * 
 * @author Administrator
 * 
 */
public abstract class Message implements Serializable {
	protected boolean proceeded = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303514006910661575L;
	private ID id;

	public ID getID() {
		return id;
	}

	public Message(ID id) {
		this.id = id;
	}

	public void remove(LogicCenter center) {
		center.removeMessage(this);
	}

	/**
	 * ������Ϣ�����ܻ��׳��쳣������RemoteException
	 * 
	 * @param center
	 * @throws Exception
	 */
	public abstract void proceed(LogicCenter center) throws RemoteException,
			MyRemoteException;

	public abstract String title();

	public abstract String detail();

	/**
	 * ����Ϣ�Ƿ�Ӧ���Զ�proceed ���Ӧ���Զ�proceed����ô��UI�ڻ�ø���Ϣ�Ժ� �Զ�����proceed()�������ǵ��û���������
	 * ͬʱ����֪�û�������һ����Ϣ���ˣ�����ѯ��Ҫɾ������Ҫproceed�� ��֪����Ժ󣬽�����Ϣ�Զ�ɾ��
	 * 
	 * @return
	 */
	public abstract boolean autoProceed();

	/**
	 * ����Ϣ�Ƿ��Ѿ�proceed��
	 * 
	 * @return
	 */
	public boolean proceeded() {
		return this.proceeded;
	}

	/**
	 * proceed�����ť�����֣��������û���Message���ѡ��proceed��
	 * ��ô����ʲô���������autoProceed��Message���������û�� ����ġ�
	 * 
	 * @return
	 */
	public abstract String proceedName();
}
