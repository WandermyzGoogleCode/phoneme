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
		//TODO û�д�����ܵĴ���
		return new BoolInfo();
	}
	
	public List<UserInfo> getContacts(){
		if (!getState().equals(VirtualState.PREPARED)){//������û��׼���õ�ʱ�������ȡ
			System.err.println("err: you can't getContacts when it's not PREPARED.");
			return null;
		}
		return contacts;
	}
}
