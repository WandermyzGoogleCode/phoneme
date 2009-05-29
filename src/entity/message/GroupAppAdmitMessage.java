package entity.message;

import entity.Group;
import entity.ID;
import entity.infoField.InfoFieldName;
import logiccenter.LogicCenter;

public class GroupAppAdmitMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1991410819539943889L;
	private boolean proceeded = false;
	private ID appUser;
	private Group group;
	
	public GroupAppAdmitMessage(ID appUser, Group group, ID mid){
		super(mid);
		this.appUser = appUser;
		this.group = group;
	}

	@Override
	public String detail() {
		return "群组："+group.getInfoField(InfoFieldName.GroupName.name())
			+"同意了你的申请，你已经加入群组。";
	}

	@Override
	public void proceed(LogicCenter center) {
		center.getDataCenter().setGroup(group);
		center.getAllGroupsBox().editGroup(group);
		proceeded = true;
	}

	@Override
	public String title() {
		return "群组："+group.getInfoField(InfoFieldName.GroupName.name())
			+"申请通过";
	}

	@Override
	public boolean autoProceed() {
		return false;
	}

	@Override
	public boolean proceeded() {
		return this.proceeded;
	}
}
