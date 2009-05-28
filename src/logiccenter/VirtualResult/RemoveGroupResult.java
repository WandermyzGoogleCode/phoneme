package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.SimpleError;

public class RemoveGroupResult extends OneTimeVirtualResult {
	private Group g;

	public RemoveGroupResult(Group g, LogicCenter center) {
		super(center);
		this.g = g;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else if (!center.getLoginUser().getID().equals(g.getAdminUserID()))
			setError(ErrorType.ID_NOT_MATCHED);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		//TODO �ɹ��Ժ��޸���Ӧ��Ⱥ����Ϣ���Լ��û���������Ⱥ����Ϣ
		return center.getServer().removeGroup(center.getLoginUser().getID(), g);
	}
}
