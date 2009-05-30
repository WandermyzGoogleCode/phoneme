package entity.message;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;

public class GroupRemovedMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1180910539422970967L;
	private Group g;
	String detail;
	
	public GroupRemovedMessage(Group g, String detail, ID mid){
		super(mid);
		this.g = g;
		this.detail = detail;
	}
	
	@Override
	public boolean autoProceed() {
		return true;
	}

	@Override
	public String detail() {
		return detail;
	}

	@Override
	public void proceed(LogicCenter center){
		center.getDataCenter().removeGroup(g);
		center.getAllGroupsBox().removeGroup(g.getID());
		proceeded = true;
	}

	@Override
	public String proceedName() {
		return "autoProceed";
	}

	@Override
	public String title() {
		return "群组："+g.getName()+"已被删除";
	}

}
