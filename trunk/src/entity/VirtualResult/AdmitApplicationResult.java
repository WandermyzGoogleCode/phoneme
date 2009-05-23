package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ID;
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
	
	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO ����ɹ���Ӧ�ø�����Ӧ��Ⱥ����Ϣ��
		return center.getServer().admitApplication(center.getLoginUser().getID(), gid, uid);
	}

	public AdmitApplicationResult(ID gid, ID uid, LogicCenter center){
		super(center);
		this.gid = gid;
		this.uid = uid;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else
			thread.start();
	}
}
