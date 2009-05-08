package logiccenter;
import entity.*;
import java.util.ArrayList;

public interface LogicCenter {
	/**
	 * Return the login user, null if not login yet
	 * 
	 * @return the login user
	 */
	public BaseUserInfo getLoginUser();
	
	public ReturnType register(EmailAddr e,Password pwd,BaseUserInfo b);
	
	public ReturnType login(EmailAddr e,Password pwd);
	
	public ReturnType editMyBaseInfo();
	
	//!!
	public ReturnType searchGroup(Group g);
	
	public ReturnType searchUser(BaseUserInfo b);
	
	public ReturnType addSynContact(UserName un);
	
	public ReturnType removeSynContact(UserName un);
	
	public ReturnType addPerContact(UserName un);
	
	public ReturnType removePerContact(UserName un);
	
	public ReturnType createGroup(Group g, Permission p);
	
	public ReturnType setGroupPermission(Group g, Permission p);
	
	public ReturnType removeGroup(Group g);
	
	public ReturnType editGroup(Group g);
	
	public ReturnType inviteToGroup(UserName un, String inviteInfo);
	
	public ReturnType removeGroupMember(UserName un, Group g);
	
	public ReturnType applyJoinGroup(ID gID);
	
	public ReturnType quitGroup(ID gID, String reason);
	
	public ReturnType admitInvitation(ID gID);
	
	public ReturnType admitApplication(ID gID, ID uID);
	
	public ReturnType setPermission(ID uid, Permission p);
	
	public ReturnType setVisibility(ID uid, int visibility);

	//�ж�һЩ�Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo
	//ͬ������
	public ReturnType editContactInfo(UserInfo cInfo);
	
	public ArrayList<UserInfo> localSearchContacts(UserInfo uInfo);
	
	/**
	 * ����ͳ����Ϣ
	 * 
	 * @return
	 */
	public StatResult getStatResult();
	
	public ReturnType importFile(String fileName);
	
	public ReturnType exportFile(String fileName);
	
	public ReturnType relationCube(UserName a, UserName b);
}
