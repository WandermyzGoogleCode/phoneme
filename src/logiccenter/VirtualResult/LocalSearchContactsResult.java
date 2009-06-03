package logiccenter.VirtualResult;

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
		center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult(){
		contacts = center.searchContacts(info, matcher);
		return new BoolInfo();
	}
	
	public List<UserInfo> getContacts(){
		if (getState() != VirtualState.PREPARED){//保护在没有准备好的时候就来索取
			System.err.println("err: you can't getContacts when it's not PREPARED.");
			return null;
		}
		return contacts;
	}
}
