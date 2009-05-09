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
	/**
	 * ע���û���ʹ��e��Ϊ�û���¼����pwdΪ���룬bΪ�û��Ļ�����Ϣ
	 * @param e
	 * @param pwd
	 * @param b
	 * @return
	 */
	public ReturnType register(EmailAddr e,Password pwd,BaseUserInfo b);
	/**
	 * �û���¼��ʹ��e��pwd
	 * @param e
	 * @param pwd
	 * @return
	 */
	public ReturnType login(EmailAddr e,Password pwd);
	/**
	 * �༭�û���Ϣ
	 * @return
	 */
	public ReturnType editMyBaseInfo();
	
	//!!
	/**
	 * ��ѯȺ��
	 * @param g
	 * @return
	 */
	public ReturnType searchGroup(Group g);
	/**
	 * ��ѯ�û�
	 * @param b
	 * @return
	 */
	public ReturnType searchUser(BaseUserInfo b);
	/**
	 * ����ͬ����ϵ�ˣ�
	 * @param un
	 * @return
	 */
	public ReturnType addSynContact(UserName un);
	/**
	 * ɾ��ͬ����ϵ��
	 * @param un
	 * @return
	 */
	public ReturnType removeSynContact(UserName un);
	/**
	 * ������Ȩ��ϵ��
	 * @param un
	 * @return
	 */
	public ReturnType addPerContact(UserName un);
	/**
	 * ɾ����Ȩ��ϵ��
	 * @param un
	 * @return
	 */
	public ReturnType removePerContact(UserName un);
	/**
	 * ����Ⱥ��g�������贴����Ȩ��
	 * @param g
	 * @param p
	 * @return
	 */
	public ReturnType createGroup(Group g, Permission p);
	/**
	 * ����Ⱥ��Ȩ��
	 * @param g
	 * @param p
	 * @return
	 */
	public ReturnType setGroupPermission(Group g, Permission p);
	/**
	 * ɾ��Ⱥ��
	 * @param g
	 * @return
	 */
	public ReturnType removeGroup(Group g);
	/**
	 * �޸�Ⱥ����Ϣ
	 * @param g
	 * @return
	 */
	public ReturnType editGroup(Group g);
	//TODO ���� �������һ��group �����ɣ�
	/**
	 * ����un����Ⱥ�飬inviteInfoΪ������Ϣ
	 * @param un
	 * @param inviteInfo
	 * @return
	 */
	public ReturnType inviteToGroup(UserName un, String inviteInfo);
	/**
	 * ɾ��Ⱥ��g�г�Աun��
	 * @param un
	 * @param g
	 * @return
	 */
	public ReturnType removeGroupMember(UserName un, Group g);
	/**
	 * �������Ⱥ��gID
	 * @param gID
	 * @return
	 */
	public ReturnType applyJoinGroup(ID gID);
	/**
	 * �˳�Ⱥ��gID,�����˳�ԭ��reason����Ϊ��
	 * @param gID
	 * @param reason
	 * @return
	 */
	public ReturnType quitGroup(ID gID, String reason);
	/**
	 * ͬ�����Ⱥ��gID������
	 * @param gID
	 * @return
	 */
	public ReturnType admitInvitation(ID gID);
	/**
	 * ͬ���û�uID����gID������
	 * @param gID
	 * @param uID
	 * @return
	 */
	public ReturnType admitApplication(ID gID, ID uID);
	/**
	 * ����uid��Ȩ��p
	 * �����û��Ļ�����Ϣ�е�ÿһ���ֶΣ������û�ѡ��һЩ�ֶΣ����û�ѡ�����������ߡ�����������
	 * @param uid
	 * @param p
	 * @return
	 */
	public ReturnType setPermission(ID uid, Permission p);
	//TODO ����Ǹ�������š���
	/**
	 *
	 * @param uid
	 * @param visibility
	 * @return
	 */
	public ReturnType setVisibility(ID uid, int visibility);

	//�ж�һЩ�Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo
	//ͬ������
	/**
	 * �޸���ϵ����Ϣ
	 * @param cInfo
	 * @return
	 */
	public ReturnType editContactInfo(UserInfo cInfo);
	/**
	 * ��ϵ�˲�ѯ
	 * @param uInfo
	 * @return
	 */
	public ArrayList<UserInfo> localSearchContacts(UserInfo uInfo);
	
	/**
	 * ����ͳ����Ϣ
	 * 
	 * @return
	 */
	public StatResult getStatResult();
	/**
	 * ����
	 * @param fileName
	 * @return
	 */
	public ReturnType importFile(String fileName);
	/**
	 * ����
	 * @param fileName
	 * @return
	 */
	public ReturnType exportFile(String fileName);
	/**
	 * ����������ʾa��b֮��ļ����ϵ��
	 * @param a
	 * @param b
	 * @return
	 */
	public ReturnType relationCube(UserName a, UserName b);
}