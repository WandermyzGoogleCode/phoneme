package entity.message;

import java.rmi.RemoteException;

import entity.Group;
import entity.ID;
import logiccenter.LogicCenter;

public class GroupUpdatedMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1405314662936026380L;
	private Group g;
	private String detail;

	public GroupUpdatedMessage(Group g, String detail, ID mID){
		super(mID);
		this.detail = detail;
		this.g = g;
	}
	
	@Override
	public boolean autoProceed() {
		return true;
	}

	@Override
	public String detail() {
		return this.detail;
	}

	@Override
	public void subproceed(LogicCenter center) throws RemoteException {
		center.getDataCenter().setGroup(g);
		center.getAllGroupsBox().editGroup(g);
	}

	@Override
	public String title() {
		return "群组\""+g.getName()+"\"有更新。";
	}

	@Override
	public String proceedName() {
		return "autoProceed";
	}

}
