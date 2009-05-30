package entity.message;

import java.rmi.RemoteException;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.Permission;
import entity.SimpleError;
import logiccenter.LogicCenter;

public class ApplyJoinGroupMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114730878679477503L;
	private ID adminUser;
	private Group g;
	private Permission p;
	private int visibility;
	private BaseUserInfo appUser;

	public ApplyJoinGroupMessage(BaseUserInfo appUser, Group g, Permission p, int visibility, ID adminUser, ID mID){
		super(mID);
		this.appUser = appUser;
		this.g = g;
		this.p = p;
		this.visibility = visibility;
		this.adminUser = adminUser;
	}
	@Override
	public boolean autoProceed() {
		return false;
	}

	@Override
	public String detail() {
		return "�û���"+appUser.getStringValue()+"�����������Ⱥ�飺"+g.getName()+"�������ɼ�����Ϊ��"+Integer.toString(visibility);
	}

	@Override
	public void proceed(LogicCenter center) throws RemoteException, MyRemoteException {
		BoolInfo res = center.getServer().admitApplication(center.getLoginUser().getID(), g.getID(), appUser.getID(), p, visibility);
		if (!res.isTrue())
			throw new MyRemoteException(new SimpleError(res.getInfo()));
		proceeded = true;
	}

	@Override
	public String title() {
		return "�û���"+appUser.getName()+"�����������Ⱥ��";
	}
	@Override
	public String proceedName() {
		return "ͬ������";
	}
}
