package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.List;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.MyRemoteException;
import entity.infoField.IdenticalInfoField;

public class RelationCubeResult extends OneTimeVirtualResult {
	private IdenticalInfoField from, to;
	private List<BaseUserInfo> res;

	public RelationCubeResult(IdenticalInfoField from, IdenticalInfoField to,
			LogicCenter center) {
		super(center);
		this.from = from;
		this.to = to;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		this.res = center.getServer().searchRelationCube(from, to);
		return new BoolInfo();
	}
	
	/**
	 * 获取搜索的结果List.get(0)解释from，List.get(List.size()-1)就是to，中间
	 * 中中间联系人。
	 * @return
	 */
	public List<BaseUserInfo> getSearchRes(){
		return res;
	}
}
