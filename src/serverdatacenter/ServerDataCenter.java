package serverdatacenter;
import java.util.List;
import entity.*;
import entity.infoField.*;
import entity.message.Message;
import algorithm.*;

public interface ServerDataCenter {
	/**
	 * 将info上的IdenticalInfoField绑定到该注册用户的id上，以便登录的时候可以通过
	 * 它们来得到id。id是由系统自动生成的，一个id可以对应到多个IdenticalInfoField
	 * pwd为用户的密码
	 * @param e
	 * @param info
	 * @return
	 */
	public ReturnType register(BaseUserInfo info, Password pwd);

	/**
	 * 如果idField对应用户的密码和pwd相等，则
	 * 返回idField对应用户的id，否则返回一个nullID，表示
	 * 用户不存在或者密码不相等。
	 * @param e
	 * @return
	 */
	public ID loginGetInfo(IdenticalInfoField idField, Password pwd);
	
	/**
	 * 将ID为uid的用户的存于服务器的信息（BaseUserInfo）更新为b
	 * @param uid 用户ID
	 * @param b 新的用户信息
	 * @return
	 */
	public ReturnType setBaseUserInfo(ID uid, BaseUserInfo b);
	
	/**
	 * uid1增加同步联系人uid2
	 * @param visibility uid1到uid2的关系的可见层次为visibility
	 * @param uid1 实行该操作的用户
	 * @param uid2 同步联系人的ID
	 * @return
	 */
	public ReturnType addSynRelationship(ID uid1, ID uid2, int visibility);
	
	/**
	 * uid1删除同步联系人uid2
	 * @param uid1 实行该操作的用户
	 * @param uid2 同步联系人的ID
	 * @return
	 */
	public ReturnType removeSynRelationship(ID uid1, ID uid2);
	
	/**
	 * 增加被授权联系人关系，（从uid1到uid2)(add permitted relationship)
	 * @param permission 该被授权联系人的权限
	 * @param uid1 实行该操作的用户
	 * @param uid2 被授权联系人的ID
	 * @return
	 */
	public ReturnType addPerRelationship(ID uid1, ID uid2, Permission permission);

	/**
	 * 删除被授权联系人关系，（从uid1到uid2)
	 * @param uid1 实行该操作的用户
	 * @param uid2 被授权联系人的ID
	 * @return
	 */
	public ReturnType removePerRelationship(ID uid1, ID uid2);
	
	/**
	 * 建立或修改群组g
	 * @param g
	 * @return
	 */
	public ReturnType setGroup(Group g);
	
	/**
	 * 将用户uid加入群组g中，uid给g的权限为permission
	 * @param g 群组
	 * @param uid 需要添加的用户ID
	 * @param permission ID为uid的用户给群组内用户的权限
	 * @return
	 */
	public ReturnType addToGroup(Group g, ID uid, Permission permission);
	
	/**
	 * 将用户uid从群组g中删除
	 * @param g 群组
	 * @param uid 需要删除的用户ID
	 * @return
	 */
	public ReturnType removeFromGroup(Group g, ID uid);
	
	/**
	 * 删除群组g
	 * @param g 群组
	 * @return
	 */
	public ReturnType removeGroup(Group g);
	
	/**
	 * 把用用户uid的一个未发送到消息（比如申请、邀请、通知等）缓存下来，等待其上线的时候发送。
	 * 该缓存应存入数据库中防止服务器DOWN掉以后数据丢失。
	 * @param uid
	 * @param msg
	 * @return
	 */
	public ReturnType addMessageBuffer(ID uid, Message msg);
	
	/**
	 * 把用用户uid的一个未发送到消息（比如申请、邀请、通知等）从缓存中删除。
	 * 
	 * 返回的类中有两个值，一个指示成功与否(BoolInfo.isTrue())，一个显示
	 * 相应的解释（比如错误信息）
	 * @param uid
	 * @param msg
	 * @return
	 */
	public BoolInfo removeMessageBuffer(ID uid, Message msg);
	
	/**
	 * 获得用户uid的缓存了的未发送到消息（比如申请、邀请、通知等）。
	 * 返回所以该用户的消息
	 * @param uid 
	 * @return
	 */
	public List<Message> getMessageBuffer(ID uid);
	
	/**
	 * 返回uid用户所有同步联系人的ID
	 * @return
	 */
	public ReturnType getSynContactID(ID uid);

	/**
	 * 返回uid用户所有被授权联系人的ID
	 * @param uid
	 * @return
	 */
	public ReturnType getPerContactID(ID uid);
	
	/**
	 * 返回idList中所有ID对应的用户的BaseUserInfo
	 * idList长度为1的时候，就是查询一个用户的BaseUserInfo
	 * @param idList
	 * @return
	 */
	public List<BaseUserInfo> getUsersInfo(List<ID> idList);
	
	/**
	 * 返回uid到targetIDList中所有ID的授权
	 * 如果uid的被授权联系人中没有定义到targetIDList中某个ID的授权，那么返回一个NullObject。
	 * @param uid
	 * @param targetIDList
	 * @return
	 */
	public ReturnType getPermissions(ID uid, List<ID> targetIDList);
	
	/**
	 * 设定用户uid1到用户uid2的权限
	 * @param uid1
	 * @param uid2
	 * @return
	 */
	public ReturnType setPermission(ID uid1, ID uid2, Permission permission);
	
	/**
	 * 修改用户uid到群组g的权限
	 * @param uid
	 * @param g
	 * @return
	 */
	public ReturnType setGroupPermission(ID uid, Group g);
	
	/**
	 * 返回用户uid对群组g所定义的权限
	 * @param uid
	 * @param g
	 * @return
	 */
	public ReturnType getGroupPermission(ID uid, Group g);
	
	/**
	 * 返回uid到targetIDList中所有ID的关系可见性
	 * 如果uid的同步联系人中没有定义到targetIDList中某个ID的关系可见性，那么返回一个0。
	 * @param uid
	 * @param targetIDList
	 * @return
	 */
	public ReturnType getVisibilities(ID uid, List<ID> targetIDList);
	
	/**
	 * 对于搜索的info，返回所有匹配上的用户
	 * @param info
	 * @return
	 */
	public ReturnType searchUser(BaseUserInfo info);
	
	/**
	 * 根据IdenticalInfoField搜索用户的ID
	 * 若为找到匹配用户，则返回nullID，不要返回null
	 * @param idInfo
	 * @return
	 */
	public ID searchUserID(IdenticalInfoField idInfo);
	
	/**
	 * 设定用户uid1到用户uid2的关系可见性
	 * @param uid1
	 * @param uid2
	 * @param visibility
	 * @return
	 */
	public ReturnType setVisiblity(ID uid1, ID uid2, int visibility);
}
