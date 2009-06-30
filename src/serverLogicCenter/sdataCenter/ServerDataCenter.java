package serverLogicCenter.sdataCenter;
import java.sql.SQLException;
import java.util.List;
import entity.*;
import entity.infoField.*;
import entity.message.Message;

public interface ServerDataCenter {
	/**
	 * ��info�ϵ�IdenticalInfoField�󶨵���ע���û���id�ϣ��Ա��¼��ʱ�����ͨ��
	 * �������õ�id��id����ϵͳ�Զ����ɵģ�һ��id���Զ�Ӧ�����IdenticalInfoField
	 * pwdΪ�û�������
	 * @param e
	 * @param info
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType register(BaseUserInfo info, Password pwd) throws SQLException;

	/**
	 * ���idField��Ӧ�û��������pwd��ȣ���
	 * ����idField��Ӧ�û���id�����򷵻�һ��nullID����ʾ
	 * �û������ڻ������벻��ȡ�
	 * @param e
	 * @return
	 * @throws SQLException 
	 */
	public ID loginGetInfo(IdenticalInfoField idField, Password pwd) throws SQLException;
	
	/**
	 * ��IDΪuid���û��Ĵ��ڷ���������Ϣ��BaseUserInfo������Ϊb
	 * @param uid �û�ID
	 * @param b �µ��û���Ϣ
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType setBaseUserInfo(ID uid, BaseUserInfo b) throws SQLException;
	
	/**
	 * uid1����ͬ����ϵ��uid2
	 * @param visibility uid1��uid2�Ĺ�ϵ�Ŀɼ����Ϊvisibility
	 * @param uid1 ʵ�иò������û�
	 * @param uid2 ͬ����ϵ�˵�ID
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType addSynRelationship(ID uid1, ID uid2, int visibility) throws SQLException;
	
	/**
	 * uid1ɾ��ͬ����ϵ��uid2
	 * @param uid1 ʵ�иò������û�
	 * @param uid2 ͬ����ϵ�˵�ID
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType removeSynRelationship(ID uid1, ID uid2) throws SQLException;
	
	/**
	 * ���ӱ���Ȩ��ϵ�˹�ϵ������uid1��uid2)(add permitted relationship)
	 * @param permission �ñ���Ȩ��ϵ�˵�Ȩ��
	 * @param uid1 ʵ�иò������û�
	 * @param uid2 ����Ȩ��ϵ�˵�ID
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType addPerRelationship(ID uid1, ID uid2, Permission permission) throws SQLException;

	/**
	 * ɾ������Ȩ��ϵ�˹�ϵ������uid1��uid2)
	 * @param uid1 ʵ�иò������û�
	 * @param uid2 ����Ȩ��ϵ�˵�ID
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType removePerRelationship(ID uid1, ID uid2) throws SQLException;
	
	/**
	 * �������޸�Ⱥ��g
	 * @param g
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType setGroup(Group g) throws SQLException;
	
	/**
	 * ���û�uid����Ⱥ��g�У�uid��g��Ȩ��Ϊpermission
	 * @param g Ⱥ��
	 * @param uid ��Ҫ��ӵ��û�ID
	 * @param permission IDΪuid���û���Ⱥ�����û���Ȩ��
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType addToGroup(Group g, ID uid, Permission permission) throws SQLException;
	
	/**
	 * ���û�uid��Ⱥ��g��ɾ��
	 * @param g Ⱥ��
	 * @param uid ��Ҫɾ�����û�ID
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType removeFromGroup(Group g, ID uid) throws SQLException;
	
	/**
	 * ɾ��Ⱥ��g
	 * @param g Ⱥ��
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType removeGroup(Group g) throws SQLException;
	
	/**
	 * �����û�uid��һ��δ���͵���Ϣ���������롢���롢֪ͨ�ȣ������������ȴ������ߵ�ʱ���͡�
	 * �û���Ӧ�������ݿ��з�ֹ������DOWN���Ժ����ݶ�ʧ��
	 * @param uid
	 * @param msg
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType addMessageBuffer(ID uid, Message msg) throws SQLException;
	
	/**
	 * �����û�uid��һ��δ���͵���Ϣ���������롢���롢֪ͨ�ȣ��ӻ�����ɾ����
	 * 
	 * ���ص�����������ֵ��һ��ָʾ�ɹ����(BoolInfo.isTrue())��һ����ʾ
	 * ��Ӧ�Ľ��ͣ����������Ϣ��
	 * @param uid
	 * @param msg
	 * @return
	 * @throws SQLException 
	 */
	public void removeMessageBuffer(ID uid, Message msg) throws SQLException;
	
	/**
	 * ����û�uid�Ļ����˵�δ���͵���Ϣ���������롢���롢֪ͨ�ȣ���
	 * �������Ը��û�����Ϣ
	 * @param uid 
	 * @return
	 * @throws SQLException 
	 */
	public List<Message> getMessageBuffer(ID uid) throws SQLException;
	
	/**
	 * ����uid�û�����ͬ����ϵ�˵�ID
	 * @return
	 * @throws SQLException 
	 */
	public List<ID> getSynContactID(ID uid) throws SQLException;

	/**
	 * ����uid�û����б���Ȩ��ϵ�˵�ID
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public List<ID> getPerContactID(ID uid) throws SQLException;
	
	/**
	 * �ж�id1�Ƿ���id2��ͬ����ϵ��
	 * @param id1
	 * @param id2
	 * @return
	 * @throws SQLException
	 */
	public boolean isSynContact(ID id1, ID id2) throws SQLException;
	
	/**
	 * �ж�id1�Ƿ���id2�ı���Ȩ��ϵ�ˡ�
	 * Ϊ��Ч�ʣ�����ר����������һ���������������Ӧ
	 * �������ٶȡ�
	 * @return
	 * @throws SQLException 
	 */
	public boolean isPerContact(ID id1, ID id2) throws SQLException;
	
	/**
	 * ����idList������ID��Ӧ���û���BaseUserInfo
	 * idList����Ϊ1��ʱ�򣬾��ǲ�ѯһ���û���BaseUserInfo
	 * @param idList
	 * @return
	 * @throws SQLException 
	 */
	public List<BaseUserInfo> getUsersInfo(List<ID> idList) throws SQLException;
	
	/**
	 * ����uid��targetIDList������ID����Ȩ
	 * ���uid�ı���Ȩ��ϵ����û�ж��嵽targetIDList��ĳ��ID����Ȩ����ô����һ��NullObject��
	 * @param uid
	 * @param targetIDList
	 * @return
	 * @throws SQLException 
	 */
	public List<Permission> getPermissions(ID uid, List<ID> targetIDList) throws SQLException;
	
	/**
	 * �趨id1��id2��Ȩ��
	 * ����id�������û�ID��Ҳ������Ⱥ��ID�������Ǵ���ȫ�ֵ�ID��
	 * ������Щ���ݿⲻ�ù���
	 * @param uid1
	 * @param uid2
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType setPermission(ID id1, ID id2, Permission permission) throws SQLException;

	/**
	 * �����û�uid���м����Ⱥ��
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public List<Group> getGroups(ID uid) throws SQLException;

	/**
	 * ��ȡȺ��IDΪgid��Ⱥ��
	 * �����ڷ���null
	 * @param gid
	 * @return
	 * @throws SQLException 
	 */
	public Group getGroup(ID gid) throws SQLException;
	
	/**
	 * ����uid��targetIDList������ID�Ĺ�ϵ�ɼ���
	 * ���uid��ͬ����ϵ����û�ж��嵽targetIDList��ĳ��ID�Ĺ�ϵ�ɼ��ԣ���ô����һ��0��
	 * @param uid
	 * @param targetIDList
	 * @return
	 * @throws SQLException 
	 */
	public List<Integer> getVisibilities(ID uid, List<ID> targetIDList) throws SQLException;
	
	/**
	 * ����������info����������ƥ���ϵ��û�
	 * @param info
	 * @return
	 * @throws SQLException 
	 */
	public List<BaseUserInfo> searchUser(BaseUserInfo info) throws SQLException;
	
	/**
	 * ����������info����������ƥ���ϵ���
	 * @param g
	 * @return
	 * @throws SQLException 
	 */
	public List<Group> searchGroup(Group info) throws SQLException;
	
	/**
	 * ����IdenticalInfoField�����û���ID
	 * ��Ϊ�ҵ�ƥ���û����򷵻�nullID����Ҫ����null
	 * @param idInfo
	 * @return
	 * @throws SQLException 
	 */
	public ID searchUserID(IdenticalInfoField idInfo) throws SQLException;
	
	/**
	 * �趨�û�uid1���û�uid2�Ĺ�ϵ�ɼ���
	 * @param uid1
	 * @param uid2
	 * @param visibility
	 * @return
	 * @throws SQLException 
	 */
	public ReturnType setVisiblity(ID uid1, ID uid2, int visibility) throws SQLException;

	public void setPwd(ID uid, Password pwd) throws SQLException;
}
