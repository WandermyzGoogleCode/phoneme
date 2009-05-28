package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.SimpleError;

/**
 * ״̬ΪPREPARED�ͱ�ʾ�Ѿ��ɹ�����
 * ERRORED��ʾ��������
 * LOADING��ʾ���ڲ���
 * @author Administrator
 *
 */
public class AdmitInvitationResult extends OneTimeVirtualResult {
	private ID gid;

	public AdmitInvitationResult(ID gid, LogicCenter center) {
		super(center);
		this.gid = gid;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO ����ɹ���Ӧ�ø������Ⱥ����Ϣ���Լ��û�����Ⱥ�����Ϣ
		return center.getServer().admitInvitation(center.getLoginUser().getID(), gid);
	}

}
