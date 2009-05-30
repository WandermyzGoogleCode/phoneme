package entity.message;

import entity.Group;
import entity.ID;
import entity.Permission;
import entity.infoField.InfoFieldName;
import logiccenter.LogicCenter;

public class GroupAppAdmitMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1991410819539943889L;
	private ID appUser;
	private Group group;
	private Permission p;
	private int visibility;
	
	public GroupAppAdmitMessage(ID appUser, Group group, Permission p, int visibility, ID mid){
		super(mid);
		this.appUser = appUser;
		this.group = group;
		this.visibility = visibility;
		this.p = p;
	}

	@Override
	public String detail() {
		return "群组："+group.getInfoField(InfoFieldName.GroupName.name())
			+"同意了你的申请，你已经加入群组。";
	}

	@Override
	public void proceed(LogicCenter center) {
		center.getDataCenter().setGroup(group);
		center.getDataCenter().setPermission(group.getID(), p);
		center.getDataCenter().setVisibility(group.getID(), visibility);
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
		return true;
	}

	@Override
	public String proceedName() {
		return "autoProceed";
	}

}
