package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.List;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.Group;
import entity.MyRemoteException;

public class SearchGroupResult extends OneTimeVirtualResult {
	private Group g;
	private List<Group> res;

	public SearchGroupResult(Group g, LogicCenter center) {
		super(center);
		this.g = g;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		res = center.getServer().searchGroup(g);
		return new BoolInfo();
	}
	
	public List<Group> getSearchRes(){
		return res;
	}
}
