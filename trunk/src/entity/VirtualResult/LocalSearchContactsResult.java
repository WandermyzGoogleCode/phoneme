package entity.VirtualResult;

import java.util.List;

import algorithm.Matcher;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.UserInfo;

public class LocalSearchContactsResult extends OneTimeVirtualResult {
	private UserInfo info;
	private Matcher matcher;
	private List<UserInfo> contacts;

	public LocalSearchContactsResult(UserInfo info,
			Matcher userMatcher, LogicCenter center) {
		super(center);
		this.info = info;
		matcher = userMatcher;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws Exception {
		contacts = center.searchContacts(info, matcher);
		//TODO û�д�����ܵĴ���
		return new BoolInfo();
	}
	
	public List<UserInfo> getContacts(){
		if (getState() != VirtualState.PREPARED){//������û��׼���õ�ʱ�������ȡ
			System.err.println("err: you can't getContacts when it's not PREPARED.");
			return null;
		}
		return contacts;
	}
}
