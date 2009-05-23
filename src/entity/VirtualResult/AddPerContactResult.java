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
 * @author Administrator
 *
 */
public class AddPerContactResult extends OneTimeVirtualResult {
	private ID thisUser;
	IdenticalInfoField targetUser;
	
	@Override
	protected BoolInfo getResult() throws RemoteException{
		BoolInfo res = center.getServer().addPerContact(thisUser, targetUser);
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
	public AddPerContactResult(ID thisUser, IdenticalInfoField un, LogicCenter center){
		super(center);
		this.thisUser = thisUser;
		this.targetUser = un;
		if (noLoginUser())
			setError(new SimpleError("not login"));
		else
			thread.start();
	}
}
