package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.Permission;
import entity.SimpleError;

/**
 * ״̬ΪPREPARED�ͱ�ʾ�Ѿ��ɹ�����
 * ERRORED��ʾ��������
 * LOADING��ʾ���ڲ���
 * @author Administrator
 *
 */
public class AdmitApplicationResult extends OneTimeVirtualResult {
	private ID gid, uid;
	private Permission p;
	int visibility;
	
	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().admitApplication(center.getLoginUser().getID(), gid, uid, p, visibility);
	}

	public AdmitApplicationResult(ID gid, ID uid, Permission p, int visibility, LogicCenter center){
		super(center);
		this.gid = gid;
		this.uid = uid;
		this.p = p;
		this.visibility = visibility;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}
}
