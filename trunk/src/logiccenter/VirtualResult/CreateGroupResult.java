package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.Permission;
import entity.SimpleError;

public class CreateGroupResult extends OneTimeVirtualResult {
	private Group g;
	private Permission p;

	public CreateGroupResult(Group g, Permission p,
			LogicCenter center) {
		super(center);
		this.g = g;
		this.p = p;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO ����ɹ����������Ⱥ�飬�Լ��û���������Ⱥ�����Ϣ
		return center.getServer().createGroup(center.getLoginUser().getID(), g, p);
	}

}
