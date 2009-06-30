package ui;
import java.util.List;

import entity.BaseUserInfo;
import entity.Group;
import entity.UserInfo;

public interface Gui {
	/**
	 * 对于userList中的每一个用户，都问同一个问题askInfo，将回答的结果返回。
	 * 
	 * ！由于当前没有本地同步，这个函数可以先不写了
	 * 
	 * @param userList
	 * @return
	 */
	public List<Boolean> checkUserList(List<UserInfo> userList, String askInfo);
	
	/**
	 * 回答一个问题，yes or no
	 * @param askInfo
	 * @return
	 */
	public boolean yesOrNo(String askInfo);
	
	public void showError(String info);

	/**
	 * ！由于当前没有本地同步，这个函数可以先不写了
	 * @param a
	 * @param list
	 * @return
	 */
	public UserInfo selMatchedUser(UserInfo a, List<UserInfo> list);
	
	/**
	 * 让用户选择返回的用户信息中的每一个字段是来源于a还是来源于b
	 * 最后将结果合并入a，返回成功与否
	 */
	public boolean mergeUserInfo(UserInfo a, UserInfo b);
	
	/**
	 * 进入加targetUser为被授权联系人的流程。
	 * 主要过程有：1、获得对targetUser的权限设置；2、调用logicCenter的接口，监控结果。 
	 * @param targetUser
	 */
	public void addPerContact(BaseUserInfo targetUser);
	
	/**
	 * 进入加targetUser为同步练习人的流程。
	 * 主要过程有：1、获得对targetUser的关系可见度；2、调用logicCenter的接口，监控结果。
	 * @param targetUser
	 */
	public void addSynContact(BaseUserInfo targetUser);

	/**
	 * 进入接受群组g邀请的流程。
	 * 主要过程有：1、获得用户对群组的权限设置和可见度设置；2、调用logicCenter的接口，监控结果。
	 * @param g
	 */
	public void admitInvitation(Group g);

}
