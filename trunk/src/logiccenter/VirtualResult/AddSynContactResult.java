package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.MyRemoteException;
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
	private int visibility;
	
	/**
	 * thisUser����Ӧ�ô���������Ӧ�ô�center�Լ���ȡ�ġ�����ûʱ�䣬��ʱ���ٸ�֮��
	 * @param thisUser
	 * @param un
	 * @param center
	 */
	public AddSynContactResult(ID thisUser, IdenticalInfoField targetUser, int visibility,
			LogicCenter center) {
		super(center);
		this.visibility = visibility;
		this.thisUser = thisUser;
		this.targetUser = targetUser;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException{
		return center.getServer().addSynContact(thisUser, targetUser, visibility);
	}
}
