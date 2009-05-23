package logiccenter;
import datacenter.DataCenter;
import entity.*;

import java.util.List;

import serverLogicCenter.ServerLogicCenter;

import entity.VirtualResult.AddPerContactResult;
import entity.VirtualResult.AddSynContactResult;
import entity.VirtualResult.AdmitApplicationResult;
import entity.VirtualResult.AdmitInvitationResult;
import entity.VirtualResult.ApplyJoinGroupResult;
import entity.VirtualResult.CreateGroupResult;
import entity.VirtualResult.EditContactInfoResult;
import entity.VirtualResult.EditGroupResult;
import entity.VirtualResult.EditMyBaseInfoResult;
import entity.VirtualResult.ExportFileResult;
import entity.VirtualResult.GetStatResultResult;
import entity.VirtualResult.ImportFileResult;
import entity.VirtualResult.InviteToGroupResult;
import entity.VirtualResult.LocalSearchContactsResult;
import entity.VirtualResult.LoginResult;
import entity.VirtualResult.MessageBox;
import entity.VirtualResult.QuitGroupResult;
import entity.VirtualResult.RegisterResult;
import entity.VirtualResult.RelationCubeResult;
import entity.VirtualResult.RemoveContactInfoResult;
import entity.VirtualResult.RemoveGroupMemberResult;
import entity.VirtualResult.RemoveGroupResult;
import entity.VirtualResult.RemovePerContactResult;
import entity.VirtualResult.RemoveSynContactResult;
import entity.VirtualResult.SearchGroupResult;
import entity.VirtualResult.SearchUserResult;
import entity.VirtualResult.SetPermissionResult;
import entity.VirtualResult.SetVisibilityResult;
import entity.infoField.*;

public interface LogicCenter {
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
	 * �༭�û���Ϣ
	 * @return
	 */
	public EditMyBaseInfoResult editMyBaseInfo(BaseUserInfo baseInfo);
	
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
	public AddSynContactResult addSynContact(IdenticalInfoField un);
	
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
	public AddPerContactResult addPerContact(IdenticalInfoField un);
	
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
	public CreateGroupResult createGroup(Group g, Permission p);
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
	public ApplyJoinGroupResult applyJoinGroup(ID gID);
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
	public AdmitInvitationResult admitInvitation(ID gID);
	/**
	 * ͬ���û�uID����gID������
	 * @param gID
	 * @param uID
	 * @return
	 */
	public AdmitApplicationResult admitApplication(ID gID, ID uID);
	
	/**
	 * ����uid��Ȩ��p
	 * �����û��Ļ�����Ϣ�е�ÿһ���ֶΣ������û�ѡ��һЩ�ֶΣ����û�ѡ���������ߡ���������
	 * @param uid
	 * @param p
	 * @return
	 */
	public SetPermissionResult setPermission(ID uid, Permission p);
	
	//TODO ����Ǹ�������š���
	//ANSWER: ���B��A��ͬ����ϵ�ˣ���ô��˵��ͼ��A��B�бߡ������ߵĿɼ��Զ���Ϊ
	//visibility��Ҳ����˵����A����̾���С�ڵ���visibility���˲���ͨ������������չ����
	//����������ϵ
	/**
	 * ��������ʹ�ã��趨���Լ�����̾���Ϊvisibility���µ��˲��ܻ���Լ�����ϵ�˵Ĺ�ϵ
	 * @param uid
	 * @param visibility
	 * @return
	 */
	public SetVisibilityResult setVisibility(ID uid, int visibility);

	/*
	 * �ж��Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo��
	 * ���Ƕ���û��ע����û���������һ�������ID���Ա��޸ĵ�ʱ���ܹ�֪�����޸���һ����
	 * 
	 * ����ͬ����synchronized����Ӧ�����������object������ʱ�򣬱������ݿ��޸ĵȡ�
	*/
	/**
	 * �޸���ϵ����Ϣ
	 * @param cInfo
	 * @return
	 */
	public EditContactInfoResult editContactInfo(UserInfo cInfo);
	
	/**
	 * ��ϵ�˲�ѯ
	 * @param uInfo
	 * @return
	 */
	public LocalSearchContactsResult localSearchContacts(UserInfo uInfo);
	
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
	public List<UserInfo> searchContacts(UserInfo info);
	
	/**
	 * ɾ����ϵ��
	 * @param id
	 * @return
	 */
	public RemoveContactInfoResult removeContactInfo(ID id);
}
