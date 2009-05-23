package entity.VirtualResult;

import java.util.List;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.UserInfo;

public class LocalSearchContactsResult extends OneTimeVirtualResult {
	private UserInfo info;
	private List<UserInfo> contacts;

	public LocalSearchContactsResult(UserInfo info,
			LogicCenter center) {
		super(center);
		this.info = info;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws Exception {
		contacts = center.searchContacts(info);
		//TODO 没有处理可能的错误
		return new BoolInfo();
	}
	
	public List<UserInfo> getContacts(){
		if (!getState().equals(VirtualState.PREPARED)){//保护在没有准备好的时候就来索取
			System.err.println("err: you can't getContacts when it's not PREPARED.");
			return null;
		}
		return contacts;
	}
}
