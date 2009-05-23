package ui;
import java.util.List;

import entity.UserInfo;

public interface Gui {
	/**
	 * 对于userList中的每一个用户，都问同一个问题askInfo，将回答的结果返回。
	 * 
	 * @param userList
	 * @return
	 */
	public List<Boolean> checkUserList(List<UserInfo> userList, String askInfo);
	
	public boolean yesOrNo(String askInfo);
	
	public UserInfo selMatchedUser(UserInfo a, List<UserInfo> list);
	
	//让用户选择返回的用户信息中的每一个字段是来源于a还是来源于b
	public UserInfo mergeUserInfo(UserInfo a, UserInfo b);
}
