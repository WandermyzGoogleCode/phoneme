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
	 * 注册用户，pwd为密码，b为用户的基本信息
	 * b中的任何identical字段都可以作为用户登录的凭着，因此b的所有
	 * identical字段中，至少有一个非空。另外，b的ID一定是nullID或者
	 * 非注册用户ID，并等待服务器分配注册用户ID。
	 * @param e
	 * @param pwd
	 * @param b
	 * @return
	 */
	public RegisterResult register(BaseUserInfo b, Password pwd);
	
	/**
	 * 用户登录，使用identicalInfo和pwd
	 * @param e
	 * @param pwd
	 * @return
	 */
	public LoginResult login(IdenticalInfoField identicalInfo, Password pwd);
	/**
	 * 编辑用户信息
	 * @return
	 */
	public EditMyBaseInfoResult editMyBaseInfo(BaseUserInfo baseInfo);
	
	//!!
	/**
	 * 查询群组
	 * @param g
	 * @return
	 */
	public SearchGroupResult searchGroup(Group g);
	/**
	 * 查询用户
	 * @param b
	 * @return
	 */
	public SearchUserResult searchUser(BaseUserInfo b);
	/**
	 * 添加同步联系人，
	 * @param un
	 * @return
	 */
	public AddSynContactResult addSynContact(IdenticalInfoField un);
	
	/**
	 * 删除同步联系人
	 * @param targetID
	 * @return
	 */
	public RemoveSynContactResult removeSynContact(ID targetID);
	
	/**
	 * 添加被授权联系人
	 * @param un
	 * @return
	 */
	public AddPerContactResult addPerContact(IdenticalInfoField un);
	
	/**
	 * 删除被授权联系人
	 * @param targetID
	 * @return
	 */
	public RemovePerContactResult removePerContact(ID targetID);
	
	/**
	 * 创建群组g，并给予创建者权限
	 * @param g
	 * @param p
	 * @return
	 */
	public CreateGroupResult createGroup(Group g, Permission p);
	/**
	 * 设置群组权限
	 * @param g
	 * @param p
	 * @return
	 */
	public SetPermissionResult setGroupPermission(Group g, Permission p);
	/**
	 * 删除群组
	 * @param g
	 * @return
	 */
	public RemoveGroupResult removeGroup(Group g);
	/**
	 * 修改群组信息
	 * @param g
	 * @return
	 */
	public EditGroupResult editGroup(Group g);
	
	/**
	 * 邀请un加入群组，inviteInfo为邀请信息
	 * @param un
	 * @param inviteInfo
	 * @return
	 */
	public InviteToGroupResult inviteToGroup(IdenticalInfoField un, Group g, String inviteInfo);
	/**
	 * 删除群组g中成员un，
	 * @param un
	 * @param g
	 * @return
	 */
	public RemoveGroupMemberResult removeGroupMember(IdenticalInfoField un, Group g);
	/**
	 * 申请加入群组gID
	 * @param gID
	 * @return
	 */
	public ApplyJoinGroupResult applyJoinGroup(ID gID);
	/**
	 * 退出群组gID,输入退出原因reason可以为空
	 * @param gID
	 * @param reason
	 * @return
	 */
	public QuitGroupResult quitGroup(ID gID, String reason);
	/**
	 * 同意加入群组gID的邀请
	 * @param gID
	 * @return
	 */
	public AdmitInvitationResult admitInvitation(ID gID);
	/**
	 * 同意用户uID加入gID的申请
	 * @param gID
	 * @param uID
	 * @return
	 */
	public AdmitApplicationResult admitApplication(ID gID, ID uID);
	
	/**
	 * 设置uid的权限p
	 * 对于用户的基本信息中的每一个字段（或者用户选中一些字段），用户选择“允许“或者“不允许”。
	 * @param uid
	 * @param p
	 * @return
	 */
	public SetPermissionResult setPermission(ID uid, Permission p);
	
	//TODO 这个是干嘛的来着。。
	//ANSWER: 如果B是A的同步联系人，那么就说明图中A到B有边。这条边的可见性定义为
	//visibility，也就是说到达A的最短距离小于等于visibility的人才能通过这条边来扩展更多
	//的人立方关系
	/**
	 * 人立方中使用，设定与自己的最短距离为visibility以下的人才能获得自己与联系人的关系
	 * @param uid
	 * @param visibility
	 * @return
	 */
	public SetVisibilityResult setVisibility(ID uid, int visibility);

	/*
	 * 判断是否是同步联系人，如果是同步联系人，则不能修改BaseUserInfo。
	 * 考虑对于没有注册的用户，给予其一个特殊的ID，以便修改的时候能够知道该修改哪一个。
	 * 
	 * 对于同步（synchronized），应该留到对相关object操作的时候，比如数据库修改等。
	*/
	/**
	 * 修改联系人信息
	 * @param cInfo
	 * @return
	 */
	public EditContactInfoResult editContactInfo(UserInfo cInfo);
	
	/**
	 * 联系人查询
	 * @param uInfo
	 * @return
	 */
	public LocalSearchContactsResult localSearchContacts(UserInfo uInfo);
	
	/**
	 * 返回统计信息
	 * 
	 * @return
	 */
	public GetStatResultResult getStatResult();
	
	/**
	 * 导入
	 * @param fileName
	 * @return
	 */
	public ImportFileResult importFile(String fileName);
	
	/**
	 * 导出
	 * @param fileName
	 * @return
	 */
	public ExportFileResult exportFile(String fileName);
	/**
	 * 人立方，显示a，b之间的间隔关系等
	 * @param a
	 * @param b
	 * @return
	 */
	public RelationCubeResult relationCube(IdenticalInfoField from, IdenticalInfoField to);
	
	/**
	 * 获得当前登录用户的信息收件箱
	 * @return
	 */
	public MessageBox getMessageBox();
	
	public ServerLogicCenter getServer();
	
	public DataCenter getDataCenter();
	
	/**
	 * 获取统计信息，该方法是单线程的，可能造成阻塞
	 * @return
	 */
	public StatResult calcStat();
	
	/**
	 * 获取本地搜索结果，该方法是单线程的，可能造成阻塞
	 * @return
	 */
	public List<UserInfo> searchContacts(UserInfo info);
	
	/**
	 * 删除联系人
	 * @param id
	 * @return
	 */
	public RemoveContactInfoResult removeContactInfo(ID id);
}
