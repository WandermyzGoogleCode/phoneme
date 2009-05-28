package entity.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.Permission;
import entity.SimpleError;
import entity.infoField.IdenticalInfoField;

/**
 * ״̬ΪPREPARED�ͱ�ʾ�Ѿ��ɹ�����
 * ERRORED��ʾ��������
 * LOADING��ʾ���ڲ���
 * @author Administrator
 *
 */
public class AddPerContactResult extends OneTimeVirtualResult {
	private ID thisUser;
	private IdenticalInfoField targetUser;
	private Permission permission;
	
	@Override
	protected BoolInfo getResult() throws RemoteException{
		BoolInfo res = center.getServer().addPerContact(thisUser, targetUser, permission);
		ID targetUserID = center.getServer().getUID(targetUser);
		if (res.isTrue())
			center.getDataCenter().addPerRelationship(targetUserID);
		//TODO ��ǰû�д���dataCenter�Ĵ���		
		return res;
	}
	
	/**
	 * thisUser����Ӧ�ô���������Ӧ�ô�center�Լ���ȡ�ġ�����ûʱ�䣬��ʱ���ٸ�֮��
	 * @param thisUser
	 * @param un
	 * @param center
	 */
	public AddPerContactResult(IdenticalInfoField un, Permission permission, LogicCenter center){
		super(center);
		thisUser = center.getLoginUser().getID();
		this.permission = permission;
		this.targetUser = un;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}
}
