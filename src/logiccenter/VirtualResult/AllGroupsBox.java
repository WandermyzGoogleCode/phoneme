package logiccenter.VirtualResult;

import java.util.List;

import entity.Group;
import entity.ID;

import logiccenter.LogicCenter;

public class AllGroupsBox extends VirtualResult {
	private LogicCenter center;
	private List<Group> groups;
	
	class GetThread extends Thread{
		@Override
		public void run() {
			groups = center.getDataCenter().getAllGroups();
			setUpdateNow();
		}
	}
	
	public AllGroupsBox(LogicCenter center){
		this.center = center;
		updateAll();
	}
	
	public synchronized List<Group> getGroups(){
		return groups;
	}
	
	/**
	 * 该方法应该是LogicCenter在修改以后调用的，
	 * GUI不用这个来修改，而是用LogicCenter的接口来修改
	 * @param newInfo
	 */
	public synchronized void editGroup(Group group){
		boolean found = false;
		for(int i=0; i<groups.size(); i++)
		{
			Group g = groups.get(i);
			if (g.getID().equals(group.getID()))
			{
				groups.set(i, group);
				found = true;
				break;
			}
		}
		if (!found)
			groups.add(group);
		setUpdateNow();
	}

	public synchronized void removeContact(ID gid){
		for(int i=0; i<groups.size(); i++)
			if (groups.get(i).getID().equals(gid))
			{
				groups.remove(i);
				break;
			}
		setUpdateNow();
	}
	
	public synchronized void updateAll(){
		GetThread thread = new GetThread();
		thread.run();
	}	
}
