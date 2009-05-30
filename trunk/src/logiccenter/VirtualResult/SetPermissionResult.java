package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.ErrorType;
import entity.ID;
import entity.BoolInfo;
import entity.Permission;

/**
 * �趨��ǰ��¼�û���targetID��Ȩ��p��targetID������Ⱥ���ID��
 * Ҳ�������û���ID��
 * @author Administrator
 *
 */
public class SetPermissionResult extends OneTimeVirtualResult {
	private ID targetID;
	private Permission p;

	public SetPermissionResult(ID targetID, Permission p,
			LogicCenter center) {
		super(center);
		this.targetID = targetID;
		this.p = p;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException{
		BoolInfo res = center.getServer().setPermission(center.getLoginUser().getID(), targetID, p);
		if (res.isTrue())
			center.getDataCenter().setPermission(targetID, p);
		return res;
	}

}
