package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ID;
import entity.SimpleError;
import entity.infoField.IdenticalInfoField;

/**
 * ״̬ΪPREPARED�ͱ�ʾ�Ѿ��ɹ�����
 * ERRORED��ʾ��������
 * LOADING��ʾ���ڲ���
 * 
 * ע�⣬���ͬ����ϵ�˳ɹ�ֻ��Ϊ����������Ѿ�
 * �ɹ��ϴ������������������ڵȴ���Ӧ��ϵ����׼��
 * ��ˣ��ù��̲���ֱ��ʹ����Ӧ��ϵ�����̳�Ϊ
 * ͬ����ϵ�ˡ�
 * @author Administrator
 *
 */
public class AddSynContactResult extends OneTimeVirtualResult {
	private ID thisUser;
	private IdenticalInfoField targetUser;
	
	/**
	 * thisUser����Ӧ�ô���������Ӧ�ô�center�Լ���ȡ�ġ�����ûʱ�䣬��ʱ���ٸ�֮��
	 * @param thisUser
	 * @param un
	 * @param center
	 */
	public AddSynContactResult(ID thisUser, IdenticalInfoField targetUser,
			LogicCenter center) {
		super(center);
		this.thisUser = thisUser;
		this.targetUser = targetUser;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException{
		return center.getServer().addSynContact(thisUser, targetUser);
	}
}
