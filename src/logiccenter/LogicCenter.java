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
	 * 注册用户，使用e作为用户登录名，pwd为密码，b为用户的基本信息
	 * @param e
	 * @param pwd
	 * @param b
	 * @return
	 */
	public ReturnType register(EmailAddr e,Password pwd,BaseUserInfo b);
	/**
	 * 用户登录，使用e和pwd
	 * @param e
	 * @param pwd
	 * @return
	 */
	public ReturnType login(EmailAddr e,Password pwd);
	/**
	 * 编辑用户信息
	 * @return
	 */
	public ReturnType editMyBaseInfo();
	
	//!!
	/**
	 * 查询群组
	 * @param g
	 * @return
	 */
	public ReturnType searchGroup(Group g);
	/**
	 * 查询用户
	 * @param b
	 * @return
	 */
	public ReturnType searchUser(BaseUserInfo b);
	/**
	 * 添加同步联系人，
	 * @param un
	 * @return
	 */
	public ReturnType addSynContact(UserName un);
	/**
	 * 删除同步联系人
	 * @param un
	 * @return
	 */
	public ReturnType removeSynContact(UserName un);
	/**
	 * 添加授权联系人
	 * @param un
	 * @return
	 */
	public ReturnType addPerContact(UserName un);
	/**
	 * 删除授权联系人
	 * @param un
	 * @return
	 */
	public ReturnType removePerContact(UserName un);
	/**
	 * 创建群组g，并给予创建者权限
	 * @param g
	 * @param p
	 * @return
	 */
	public ReturnType createGroup(Group g, Permission p);
	/**
	 * 设置群组权限
	 * @param g
	 * @param p
	 * @return
	 */
	public ReturnType setGroupPermission(Group g, Permission p);
	/**
	 * 删除群组
	 * @param g
	 * @return
	 */
	public ReturnType removeGroup(Group g);
	/**
	 * 修改群组信息
	 * @param g
	 * @return
	 */
	public ReturnType editGroup(Group g);
	//TODO 邀请 里面该有一个group 参数吧？
	/**
	 * 邀请un加入群组，inviteInfo为邀请信息
	 * @param un
	 * @param inviteInfo
	 * @return
	 */
	public ReturnType inviteToGroup(UserName un, String inviteInfo);
	/**
	 * 删除群组g中成员un，
	 * @param un
	 * @param g
	 * @return
	 */
	public ReturnType removeGroupMember(UserName un, Group g);
	/**
	 * 申请加入群组gID
	 * @param gID
	 * @return
	 */
	public ReturnType applyJoinGroup(ID gID);
	/**
	 * 退出群组gID,输入退出原因reason可以为空
	 * @param gID
	 * @param reason
	 * @return
	 */
	public ReturnType quitGroup(ID gID, String reason);
	/**
	 * 同意加入群组gID的邀请
	 * @param gID
	 * @return
	 */
	public ReturnType admitInvitation(ID gID);
	/**
	 * 同意用户uID加入gID的申请
	 * @param gID
	 * @param uID
	 * @return
	 */
	public ReturnType admitApplication(ID gID, ID uID);
	/**
	 * 设置uid的权限p
	 * 对于用户的基本信息中的每一个字段（或者用户选中一些字段），用户选择“允许“或者“不允许”。
	 * @param uid
	 * @param p
	 * @return
	 */
	public ReturnType setPermission(ID uid, Permission p);
	//TODO 这个是干嘛的来着。。
	/**
	 *
	 * @param uid
	 * @param visibility
	 * @return
	 */
	public ReturnType setVisibility(ID uid, int visibility);

	//判断一些是否是同步联系人，如果是同步联系人，则不能修改BaseUserInfo
	//同步过程
	/**
	 * 修改联系人信息
	 * @param cInfo
	 * @return
	 */
	public ReturnType editContactInfo(UserInfo cInfo);
	/**
	 * 联系人查询
	 * @param uInfo
	 * @return
	 */
	public ArrayList<UserInfo> localSearchContacts(UserInfo uInfo);
	
	/**
	 * 返回统计信息
	 * 
	 * @return
	 */
	public StatResult getStatResult();
	/**
	 * 导入
	 * @param fileName
	 * @return
	 */
	public ReturnType importFile(String fileName);
	/**
	 * 导出
	 * @param fileName
	 * @return
	 */
	public ReturnType exportFile(String fileName);
	/**
	 * 人立方，显示a，b之间的间隔关系等
	 * @param a
	 * @param b
	 * @return
	 */
	public ReturnType relationCube(UserName a, UserName b);
}
