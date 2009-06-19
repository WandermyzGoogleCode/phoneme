package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.Permission;
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
	private Permission p;
	private int visibility;

	public AdmitInvitationResult(ID gid, Permission p, int visibility, LogicCenter center) {
		super(center);
		this.gid = gid;
		this.p = p;
		this.visibility = visibility;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		Group g = center.getServer().admitInvitation(center.getLoginUser().getID(), gid, p, visibility);
		center.getAllGroupsBox().editGroup(g);
		center.getAllGroupsBox().setPermission(gid, p);
		return new BoolInfo();
	}

}
