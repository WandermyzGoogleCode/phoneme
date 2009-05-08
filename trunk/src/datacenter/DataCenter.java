package datacenter;
import java.util.ArrayList;
import entity.*;
public interface DataCenter {
	//判断一些是否是同步联系人，如果是同步联系人，则不能修改BaseUserInfo
	public ReturnType setUserInfo(UserInfo b);	
	public ReturnType setPermission(Permission p);
	
	/**
	 * 建立或修改群组g
	 * @param g
	 * @return
	 */
	public ReturnType setGroup(Group g);
	
	public ReturnType removeGroup(Group g);
	
	/**
	 * 增加同步联系人关系，（从自己到uid用户)
	 * @param uid 同步联系人的ID
	 * @return
	 */
	public ReturnType addSynRelationship(ID uid);
	
	public ReturnType removeSynRelationship(ID uid);

	/**
	 * 增加被授权联系人关系，（从自己到uid用户)(add permitted relationship)
	 * @param uid 被授权联系人的ID
	 * @return
	 */
	public ReturnType addPerRelationship(ID uid);

	public ReturnType removePerRelationship(ID uid);
	
	public ReturnType importFile(String fileName);
	
	public ReturnType exportFile(String fileName);

	/**
	 * 把用户uid加入到群组g中
	 * @param g
	 * @param uid
	 * @return
	 */
	public ReturnType addToGroup(Group g, ID uid);
	
	/**
	 * 把用户uid从群组g中删除
	 * @param g
	 * @param uid
	 * @return
	 */
	public ReturnType removeFromGroup(Group g, ID uid);

	/***
	 * 
	 * @param source 获取用户信息的途径。比如直接从自己的数据库，或者从Outlook，Google Synchronized中等
	 * @return
	 */
	public ArrayList<UserInfo> getAllUserInfo(LocalSynSource source);
}
