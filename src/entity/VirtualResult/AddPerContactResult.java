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
 * 状态为PREPARED就表示已经成功操作
 * ERRORED表示操作出错
 * LOADING表示正在操作
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
		//TODO 当前没有处理dataCenter的错误		
		return res;
	}
	
	/**
	 * thisUser本不应该传进来，而应该从center自己获取的。现在没时间，有时间再改之。
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
