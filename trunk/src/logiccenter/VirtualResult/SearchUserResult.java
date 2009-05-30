package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.List;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.MyRemoteException;

public class SearchUserResult extends OneTimeVirtualResult {
	private BaseUserInfo b;
	private List<BaseUserInfo> res;
	
	public SearchUserResult(BaseUserInfo b, LogicCenter center) {
		super(center);
		this.b = b;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		res = center.getServer().searchUser(b);
		return new BoolInfo();
	}

	/**
	 * ��ȡ�������
	 * @return
	 */
	public List<BaseUserInfo> getSearchRes(){
		return res;
	}
}
