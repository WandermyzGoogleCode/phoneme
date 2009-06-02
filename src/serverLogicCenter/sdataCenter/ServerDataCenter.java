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
	 */
	public ReturnType register(BaseUserInfo info, Password pwd);

	/**
	 * ���idField��Ӧ�û��������pwd��ȣ���
	 * ����idField��Ӧ�û���id�����򷵻�һ��nullID����ʾ
	 * �û������ڻ������벻��ȡ�
	 * @param e
	 * @return
	 */
	public ID loginGetInfo(IdenticalInfoField idField, Password pwd);
	
	/**
	 * ��IDΪuid���û��Ĵ��ڷ���������Ϣ��BaseUserInfo������Ϊb
	 * @param uid �û�ID
	 * @param b �µ��û���Ϣ
	 * @return
	 */
	public ReturnType setBaseUserInfo(ID uid, BaseUserInfo b);
	
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
	 */
	public ReturnType removeSynRelationship(ID uid1, ID uid2);
	
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
	 */
	public ReturnType removePerRelationship(ID uid1, ID uid2);
	
	/**
	 * �������޸�Ⱥ��g
	 * @param g
	 * @return
	 */
	public ReturnType setGroup(Group g);
	
	/**
	 * ���û�uid����Ⱥ��g�У�uid��g��Ȩ��Ϊpermission
	 * @param g Ⱥ��
	 * @param uid ��Ҫ���ӵ��û�ID
	 * @param permission IDΪuid���û���Ⱥ�����û���Ȩ��
	 * @return
	 */
	public ReturnType addToGroup(Group g, ID uid, Permission permission);
	
	/**
	 * ���û�uid��Ⱥ��g��ɾ��
	 * @param g Ⱥ��
	 * @param uid ��Ҫɾ�����û�ID
	 * @return
	 */
	public ReturnType removeFromGroup(Group g, ID uid);
	
	/**
	 * ɾ��Ⱥ��g
	 * @param g Ⱥ��
	 * @return
	 */
	public ReturnType removeGroup(Group g);
	
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
	 */
	public BoolInfo removeMessageBuffer(ID uid, Message msg);
	
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
	 */
	public List<ID> getSynContactID(ID uid);

	/**
	 * ����uid�û����б���Ȩ��ϵ�˵�ID
	 * @param uid
	 * @return
	 */
	public List<ID> getPerContactID(ID uid);
	
	/**
	 * �ж�id1�Ƿ���id2�ı���Ȩ��ϵ�ˡ�
	 * Ϊ��Ч�ʣ�����ר����������һ���������������Ӧ
	 * �������ٶȡ�
	 * @return
	 */
	public boolean isPerContact(ID id1, ID id2);
	
	/**
	 * ����idList������ID��Ӧ���û���BaseUserInfo
	 * idList����Ϊ1��ʱ�򣬾��ǲ�ѯһ���û���BaseUserInfo
	 * @param idList
	 * @return
	 */
	public List<BaseUserInfo> getUsersInfo(List<ID> idList);
	
	/**
	 * ����uid��targetIDList������ID����Ȩ
	 * ���uid�ı���Ȩ��ϵ����û�ж��嵽targetIDList��ĳ��ID����Ȩ����ô����һ��NullObject��
	 * @param uid
	 * @param targetIDList
	 * @return
	 */
	public List<Permission> getPermissions(ID uid, List<ID> targetIDList);
	
	/**
	 * �趨id1��id2��Ȩ��
	 * ����id�������û�ID��Ҳ������Ⱥ��ID�������Ǵ���ȫ�ֵ�ID��
	 * ������Щ���ݿⲻ�ù���
	 * @param uid1
	 * @param uid2
	 * @return
	 */
	public ReturnType setPermission(ID id1, ID id2, Permission permission);

	/**
	 * �����û�uid���м����Ⱥ��
	 * @param uid
	 * @return
	 */
	public List<Group> getGroups(ID uid);

	/**
	 * ��ȡȺ��IDΪgid��Ⱥ��
	 * �����ڷ���null
	 * @param gid
	 * @return
	 */
	public Group getGroup(ID gid);
	
	/**
	 * ����uid��targetIDList������ID�Ĺ�ϵ�ɼ���
	 * ���uid��ͬ����ϵ����û�ж��嵽targetIDList��ĳ��ID�Ĺ�ϵ�ɼ��ԣ���ô����һ��0��
	 * @param uid
	 * @param targetIDList
	 * @return
	 */
	public List<Integer> getVisibilities(ID uid, List<ID> targetIDList);
	
	/**
	 * ����������info����������ƥ���ϵ��û�
	 * @param info
	 * @return
	 */
	public List<BaseUserInfo> searchUser(BaseUserInfo info);
	
	/**
	 * ����������info����������ƥ���ϵ���
	 * @param g
	 * @return
	 */
	public List<Group> searchGroup(Group info);
	
	/**
	 * ����IdenticalInfoField�����û���ID
	 * ��Ϊ�ҵ�ƥ���û����򷵻�nullID����Ҫ����null
	 * @param idInfo
	 * @return
	 */
	public ID searchUserID(IdenticalInfoField idInfo);
	
	/**
	 * �趨�û�uid1���û�uid2�Ĺ�ϵ�ɼ���
	 * @param uid1
	 * @param uid2
	 * @param visibility
	 * @return
	 */
	public ReturnType setVisiblity(ID uid1, ID uid2, int visibility);
}