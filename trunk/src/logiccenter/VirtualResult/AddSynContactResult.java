package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.MyRemoteException;
import entity.SimpleError;
import entity.infoField.IdenticalInfoField;

/**
 * 状态为PREPARED就表示已经成功操作
 * ERRORED表示操作出错
 * LOADING表示正在操作
 * 
 * 注意，添加同步联系人成功只以为着添加请求已经
 * 成功上传到服务器，现在正在等待相应联系人批准。
 * 因此，该过程并不直接使得相应联系人立刻成为
 * 同步联系人。
 * @author Administrator
 *
 */
public class AddSynContactResult extends OneTimeVirtualResult {
	private ID thisUser;
	private IdenticalInfoField targetUser;
	private int visibility;
	
	/**
	 * thisUser本不应该传进来，而应该从center自己获取的。现在没时间，有时间再改之。
	 * @param thisUser
	 * @param un
	 * @param center
	 */
	public AddSynContactResult(ID thisUser, IdenticalInfoField targetUser, int visibility,
			LogicCenter center) {
		super(center);
		this.visibility = visibility;
		this.thisUser = thisUser;
		this.targetUser = targetUser;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException{
		return center.getServer().addSynContact(thisUser, targetUser, visibility);
	}
}
