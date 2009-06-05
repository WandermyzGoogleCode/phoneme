package entity.message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import logiccenter.LogicCenter;
import entity.ID;
import entity.MyRemoteException;

/**
 * ���ڶԷ�ɾ���˸��輺������Ȩ����˶Է���ͬ����ϵ��������
 * @author Administrator
 *
 */
public class SynRelationLostMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5816850967963475157L;
	
	ID synUser;
	String userName;

	public SynRelationLostMessage(ID synUser, String name, ID mID){
		super(mID);
		this.synUser= synUser;
		this.userName = name;
	}
	
	@Override
	public boolean autoProceed() {
		return true;
	}

	@Override
	public String detail() {
		return "�û���"+userName+" ɾ���˶��������Ȩ��";
	}

	@Override
	public void subproceed(LogicCenter center) throws RemoteException,
			MyRemoteException {
		List<ID> idList = new ArrayList<ID>();
		idList.add(synUser);
		center.getAllContactsBox().updateRelation(idList);
	}

	@Override
	public String proceedName() {
		return "autoProceed";
	}

	@Override
	public String title() {
		return "����ʧ�˺���ϵ�ˣ�"+userName+" ��ͬ����ϵ";
	}

}
