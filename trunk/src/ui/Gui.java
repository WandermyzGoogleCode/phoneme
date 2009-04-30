package ui;
import entity.*;
import java.util.ArrayList;
import entity.ResulType;
public interface Gui {
	public void pushResult(ResulType type,Object o);

	/**
	 * 对于userList中的每一个用户，都问同一个问题askInfo，将回答的结果返回。
	 * 
	 * @param userList
	 * @return
	 */
	public ArrayList<Boolean> checkUserList(ArrayList<UserInfo> userList, String askInfo);
	
	public boolean yseOrNo(String askInfo);
	
	public UserInfo selMatchedUser(UserInfo a, ArrayList<UserInfo> list);
	
	//让用户选择返回的用户信息中的每一个字段是来源于a还是来源于b
	public UserInfo mergeUserInfo(UserInfo a, UserInfo b);
}
