package ui;
import java.util.List;

import entity.BaseUserInfo;
import entity.Group;
import entity.UserInfo;

public interface Gui {
	/**
	 * ����userList�е�ÿһ���û�������ͬһ������askInfo�����ش�Ľ�����ء�
	 * 
	 * �����ڵ�ǰû�б���ͬ����������������Ȳ�д��
	 * 
	 * @param userList
	 * @return
	 */
	public List<Boolean> checkUserList(List<UserInfo> userList, String askInfo);
	
	/**
	 * �ش�һ�����⣬yes or no
	 * @param askInfo
	 * @return
	 */
	public boolean yesOrNo(String askInfo);
	
	public void showError(String info);

	/**
	 * �����ڵ�ǰû�б���ͬ����������������Ȳ�д��
	 * @param a
	 * @param list
	 * @return
	 */
	public UserInfo selMatchedUser(UserInfo a, List<UserInfo> list);
	
	/**
	 * ���û�ѡ�񷵻ص��û���Ϣ�е�ÿһ���ֶ�����Դ��a������Դ��b
	 * ��󽫽���ϲ���a�����سɹ����
	 */
	public boolean mergeUserInfo(UserInfo a, UserInfo b);
	
	/**
	 * �����targetUserΪ����Ȩ��ϵ�˵����̡�
	 * ��Ҫ�����У�1����ö�targetUser��Ȩ�����ã�2������logicCenter�Ľӿڣ���ؽ���� 
	 * @param targetUser
	 */
	public void addPerContact(BaseUserInfo targetUser);
	
	/**
	 * �����targetUserΪͬ����ϰ�˵����̡�
	 * ��Ҫ�����У�1����ö�targetUser�Ĺ�ϵ�ɼ��ȣ�2������logicCenter�Ľӿڣ���ؽ����
	 * @param targetUser
	 */
	public void addSynContact(BaseUserInfo targetUser);

	/**
	 * �������Ⱥ��g��������̡�
	 * ��Ҫ�����У�1������û���Ⱥ���Ȩ�����úͿɼ������ã�2������logicCenter�Ľӿڣ���ؽ����
	 * @param g
	 */
	public void admitInvitation(Group g);

}
