package logiccenter;

import java.util.List;

import entity.BaseUserInfo;
import entity.Group;
import entity.ID;
import entity.Password;
import entity.Permission;
import entity.ReturnType;
import entity.StatResult;
import entity.UserInfo;
import entity.infoField.EmailAddr;
import entity.infoField.IdenticalInfoField;

public class LogicCenterImp implements LogicCenter {

	private BaseUserInfo loginUser;//当前登录的用户
	
	@Override
	public ReturnType addPerContact(IdenticalInfoField un) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType addSynContact(IdenticalInfoField un) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType admitApplication(ID gid, ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType admitInvitation(ID gid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType applyJoinGroup(ID gid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType createGroup(Group g, Permission p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType editContactInfo(UserInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType editGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType editMyBaseInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType exportFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseUserInfo getLoginUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatResult getStatResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType importFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType inviteToGroup(IdenticalInfoField un, Group g,
			String inviteInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> localSearchContacts(UserInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType login(EmailAddr e, Password pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType quitGroup(ID gid, String reason) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType register(EmailAddr e, Password pwd, BaseUserInfo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType relationCube(IdenticalInfoField a, IdenticalInfoField b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeGroupMember(IdenticalInfoField un, Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removePerContact(IdenticalInfoField un) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeSynContact(IdenticalInfoField un) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType searchGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType searchUser(BaseUserInfo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setGroupPermission(Group g, Permission p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setPermission(ID uid, Permission p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setVisibility(ID uid, int visibility) {
		// TODO Auto-generated method stub
		return null;
	}

}
