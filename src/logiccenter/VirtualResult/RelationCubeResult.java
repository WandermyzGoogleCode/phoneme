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
	 * ��ȡ�����Ľ��List.get(0)����from��List.get(List.size()-1)����to���м�
	 * ���м���ϵ�ˡ�
	 * @return
	 */
	public List<BaseUserInfo> getSearchRes(){
		return res;
	}
}
