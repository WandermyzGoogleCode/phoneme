package logiccenter;
import datacenter.DataCenter;
import entity.*;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import logiccenter.VirtualResult.*;

import algorithm.Matcher;

import serverLogicCenter.ServerLogicCenter;
import ui.Gui;

import entity.infoField.*;
import entity.message.Message;

public interface LogicCenter {
	/**
	 * ����Ϣ�ӷ������Լ�MessageBox��ɾ����
	 */
	public RemoveMessageResult removeMessage(Message message);
	
	/**
	 * Return the login user, null if not login yet
	 * 
	 * @return the login user
	 */
	public BaseUserInfo getLoginUser();
	
	/**
	 * ע���û���pwdΪ���룬bΪ�û��Ļ�����Ϣ
	 * b�е��κ�identical�ֶζ�������Ϊ�û���¼��ƾ�ţ����b������
	 * identical�ֶ��У�������һ���ǿա����⣬b��IDһ����nullID����
	 * ��ע���û�ID�����ȴ�����������ע���û�ID��
	 * @param e
	 * @param pwd
	 * @param b
	 * @return
	 */
	public RegisterResult register(BaseUserInfo b, Password pwd);
	
	/**
	 * �û���¼��ʹ��identicalInfo��pwd
	 * @param e
	 * @param pwd
	 * @return
	 */
	public LoginResult login(IdenticalInfoField identicalInfo, Password pwd);
	/**
	 * �༭�û���Ϣ�������޸����룬���pwdΪ�գ�null����isNull������ô���޸�
	 * @return
	 */
	public EditMyBaseInfoResult editMyBaseInfo(BaseUserInfo baseInfo, Password pwd);
	
	//!!
	/**
	 * ��ѯȺ��
	 * @param g
	 * @return
	 */
	public SearchGroupResult searchGroup(Group g);
	/**
	 * ��ѯ�û�
	 * @param b
	 * @return
	 */
	public SearchUserResult searchUser(BaseUserInfo b);
	/**
	 * ���ͬ����ϵ�ˣ�
	 * @param un
	 * @return
	 */
	public AddSynContactResult addSynContact(IdenticalInfoField un, int visibility);
	
	/**
	 * ɾ��ͬ����ϵ��
	 * @param targetID
	 * @return
	 */
	public RemoveSynContactResult removeSynContact(ID targetID);
	
	/**
	 * ��ӱ���Ȩ��ϵ��
	 * @param un
	 * @return
	 */
	public AddPerContactResult addPerContact(IdenticalInfoField un, Permission permission);
	
	/**
	 * ɾ������Ȩ��ϵ��
	 * @param targetID
	 * @return
	 */
	public RemovePerContactResult removePerContact(ID targetID);
	
	/**
	 * ����Ⱥ��g�������贴����Ȩ��
	 * @param g
	 * @param p
	 * @return
	 */
	public CreateGroupResult createGroup(Group g, Permission p, int visibility);
	/**
	 * ����Ⱥ��Ȩ��
	 * @param g
	 * @param p
	 * @return
	 */
	public SetPermissionResult setGroupPermission(Group g, Permission p);
	/**
	 * ɾ��Ⱥ��
	 * @param g
	 * @return
	 */
	public RemoveGroupResult removeGroup(Group g);
	/**
	 * �޸�Ⱥ����Ϣ
	 * @param g
	 * @return
	 */
	public EditGroupResult editGroup(Group g);
	
	/**
	 * ����un����Ⱥ�飬inviteInfoΪ������Ϣ
	 * @param un
	 * @param inviteInfo
	 * @return
	 */
	public InviteToGroupResult inviteToGroup(IdenticalInfoField un, Group g, String inviteInfo);
	/**
	 * ɾ��Ⱥ��g�г�Աun��
	 * @param un
	 * @param g
	 * @return
	 */
	public RemoveGroupMemberResult removeGroupMember(IdenticalInfoField un, Group g);
	/**
	 * �������Ⱥ��gID
	 * @param gID
	 * @return
	 */
	public ApplyJoinGroupResult applyJoinGroup(ID gID, Permission p, int visibility);
	/**
	 * �˳�Ⱥ��gID,�����˳�ԭ��reason����Ϊ��
	 * @param gID
	 * @param reason
	 * @return
	 */
	public QuitGroupResult quitGroup(ID gID, String reason);
	/**
	 * ͬ�����Ⱥ��gID������
	 * @param gID
	 * @return
	 */
	public AdmitInvitationResult admitInvitation(ID gID, Permission p, int visibility);
	/**
	 * ͬ���û�uID����gID������
	 * @param gID
	 * @param uID
	 * @return
	 */
	public AdmitApplicationResult admitApplication(ID gID, ID uID, Permission p, int visibility);
	
	/**
	 * ����uid��Ȩ��p
	 * �����û��Ļ�����Ϣ�е�ÿһ���ֶΣ������û�ѡ��һЩ�ֶΣ����û�ѡ���������ߡ���������
	 * @param uid
	 * @param p
	 * @return
	 */
	public SetPermissionResult setPermission(ID uid, Permission p);
	
	//���B��A��ͬ����ϵ�ˣ���ô��˵��ͼ��A��B�бߡ������ߵĿɼ��Զ���Ϊ
	//visibility��Ҳ����˵����A����̾���С�ڵ���visibility���˲���ͨ������������չ����
	//����������ϵ
	/**
	 * ��������ʹ�ã��趨���Լ�����̾���Ϊvisibility���µ��˲��ܻ���Լ�����ϵ�˵Ĺ�ϵ
	 * @param uid
	 * @param visibility
	 * @return
	 */
	public SetVisibilityResult setVisibility(ID id, int visibility);

	/*
	 * �ж��Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo��
	 * ���Ƕ���û��ע����û���������һ�������ID���Ա��޸ĵ�ʱ���ܹ�֪�����޸���һ����
	 * 
	 * ����ͬ����synchronized����Ӧ�����������object������ʱ�򣬱������ݿ��޸ĵȡ�
	*/
	/**
	 * �޸���ϵ����Ϣ
	 * ע��customUserInfo����Ϊnull����ʱ�����޸ġ�
	 * @param cInfo
	 * @return
	 */
	public EditContactInfoResult editContactInfo(UserInfo cInfo);
	
	/**
	 * ��ϵ�˲�ѯ
	 * @param uInfo
	 * @return
	 */
	public LocalSearchContactsResult localSearchContacts(UserInfo uInfo, Matcher userMatcher);
	
	/**
	 * ����ͳ����Ϣ
	 * 
	 * @return
	 */
	public GetStatResultResult getStatResult();
	
	/**
	 * ����
	 * @param fileName
	 * @return
	 */
	public ImportFileResult importFile(String fileName);
	
	/**
	 * ����
	 * @param fileName
	 * @return
	 */
	public ExportFileResult exportFile(String fileName);
	/**
	 * ����������ʾa��b֮��ļ����ϵ��
	 * @param a
	 * @param b
	 * @return
	 */
	public RelationCubeResult relationCube(IdenticalInfoField from, IdenticalInfoField to);
	
	/**
	 * ��õ�ǰ��¼�û�����Ϣ�ռ���
	 * @return
	 */
	public MessageBox getMessageBox();
	
	public ServerLogicCenter getServer();
	
	public Gui getUI();
	
	/**
	 * GUI�ڻ��LogicCenter֮��Ӧ�õ��øú�����
	 * ��LogicCenter֪��GUI
	 * @param ui
	 */
	public void setUI(Gui ui);
	
	public DataCenter getDataCenter();
	
	/**
	 * ��ȡͳ����Ϣ���÷����ǵ��̵߳ģ������������
	 * @return
	 */
	public StatResult calcStat();
	
	/**
	 * ��ȡ��������������÷����ǵ��̵߳ģ������������
	 * @return
	 */
	public List<UserInfo> searchContacts(UserInfo info, Matcher matcher);
	
	/**
	 * ɾ����ϵ��
	 * @param id
	 * @return
	 */
	public RemoveContactInfoResult removeContactInfo(ID id);
	
	/**
	 * ��ȡ���е��û���Ϣ��Virtual Proxy��
	 * @return
	 */
	public AllContactsBox getAllContactsBox();
	
	/**
	 * ��ȡ���б���Ȩ��ϵ�˵���Ϣ
	 * @return
	 */
	public AllPerContactsBox getAllPerContatcsBox();
	
	/**
	 * ��ȡ���м����Ⱥ��
	 * @return
	 */
	public AllGroupsBox getAllGroupsBox();
	
	/**
	 * ���õ�ǰ��¼���û�
	 * @param loginUser
	 */
	public void setLoginUser(BaseUserInfo loginUser);

	/**
	 * �����ȡ������Ⱥ��
	 * @param gid
	 * @return
	 */
	public Group getGroup(ID gid);
	
	/**
	 * Զ��ͬ��������ͬ���Ȳ����ˣ���Ϊ���ݿ����û��
	 * �㶨OUTLOOK
	 * @return
	 */
	public RemoteSynResult remoteSynchronize();
	
	public LocalSynResult localSynchronize();
	
	/**
	 * �����˳�֮ǰҪ�����������������߳�
	 * �����������Ų��š�
	 */
	public void logout() throws RemoteException;

	/**
	 * ����¼�û��޸����Լ���Ϣ��ʱ�����
	 * @param baseInfo
	 */
	public void editLoginUser(BaseUserInfo baseInfo);
	
	/**
	 * ��ȡһ�����̵߳�Executor���������̨���񣬱�������
	 * @return
	 */
	public ExecutorService getExecutor();
}
