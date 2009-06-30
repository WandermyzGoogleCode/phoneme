package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.List;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.MyRemoteException;

public class SearchUserResult extends OneTimeVirtualResult {
	private BaseUserInfo b;
	private List<BaseUserInfo> res;
	
	public SearchUserResult(BaseUserInfo b, LogicCenter center) {
		super(center);
		this.b = b;
		center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		if (center.getServer() == null){
			center.tryConnectServer();
			if (center.getServer() == null)
				return new BoolInfo(ErrorType.CANNOT_CONNECT_TO_SERVER);
		}
		res = center.getServer().searchUser(b);
		return new BoolInfo();
	}

	/**
	 * 获取搜索结果
	 * @return
	 */
	public List<BaseUserInfo> getSearchRes(){
		return res;
	}
}
