package logiccenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import entity.Group;
import entity.ID;
import entity.Permission;
import entity.UserInfo;

/**
 * 将AllContactsBox，AllGroupsBox以及AllPerContactsBox之间
 * 相互依赖的关系，转化为三者都依赖于该类的关系，从而避免
 * Synchronize时可能产生的环状依赖导致的死锁
 * @author Administrator
 *
 */
public class BoxContent {
	private Map<ID, UserInfo> contacts;//AllContactsBox的内容
	
	//AllGroupsBox的内容
	private Map<ID, Group> groups;
	private Map<ID, Permission> gPermissions = new ConcurrentHashMap<ID, Permission>();
	
	//AllPerContactsBox的内容
	private Map<ID, UserInfo> pContacts;
	private Map<ID, Permission> pPermissions = new ConcurrentHashMap<ID, Permission>();
	
	public synchronized void setPContacts(Map<ID, UserInfo> contacts) {
		pContacts = contacts;
	}
	
	public synchronized void setPPermissions(Map<ID, Permission> permissions) {
		pPermissions = permissions;
	}
	
	public synchronized void setContacts(Map<ID, UserInfo> contacts) {
		this.contacts = contacts;
	}
	
	public synchronized void setGPermissions(Map<ID, Permission> permissions) {
		gPermissions = permissions;
	}
	
	public synchronized void setGroups(Map<ID, Group> groups) {
		this.groups = groups;
	}
	
	public synchronized Map<ID, UserInfo> getContacts() {
		return contacts;
	}
	
	public synchronized Map<ID, Permission> getGPermissions() {
		return gPermissions;
	}
	
	public synchronized Map<ID, Group> getGroups() {
		return groups;
	}
	
	public synchronized Map<ID, UserInfo> getPContacts() {
		return pContacts;
	}
	
	public synchronized Map<ID, Permission> getPPermissions() {
		return pPermissions;
	}
}
