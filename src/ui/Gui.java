package ui;
import entity.*;
import java.util.List;
import entity.ResulType;
public interface Gui {
	public void pushResult(ResulType type, Object o);

	/**
	 * ����userList�е�ÿһ���û�������ͬһ������askInfo�����ش�Ľ�����ء�
	 * 
	 * @param userList
	 * @return
	 */
	public List<Boolean> checkUserList(List<UserInfo> userList, String askInfo);
	
	public boolean yseOrNo(String askInfo);
	
	public UserInfo selMatchedUser(UserInfo a, List<UserInfo> list);
	
	//���û�ѡ�񷵻ص��û���Ϣ�е�ÿһ���ֶ�����Դ��a������Դ��b
	public UserInfo mergeUserInfo(UserInfo a, UserInfo b);
}