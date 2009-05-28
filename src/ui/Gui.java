package ui;
import java.util.List;

import entity.BaseUserInfo;
import entity.UserInfo;

public interface Gui {
	/**
	 * ����userList�е�ÿһ���û�������ͬһ������askInfo�����ش�Ľ�����ء�
	 * 
	 * @param userList
	 * @return
	 */
	public List<Boolean> checkUserList(List<UserInfo> userList, String askInfo);
	
	public boolean yesOrNo(String askInfo);
	
	public UserInfo selMatchedUser(UserInfo a, List<UserInfo> list);
	
	//���û�ѡ�񷵻ص��û���Ϣ�е�ÿһ���ֶ�����Դ��a������Դ��b
	public UserInfo mergeUserInfo(UserInfo a, UserInfo b);
	
	/**
	 * �����targetUserΪ����Ȩ��ϵ�˵����̡�
	 * ��Ҫ�����У�1����ö�targetUser��Ȩ�����ã�2������logicCenter�Ľӿڣ���ؽ���� 
	 * @param targetUser
	 */
	public void addPerContact(BaseUserInfo targetUser);
}
