package datacenter;
import java.util.List;

import entity.*;
public interface DataCenter {
	//判断一些是否是同步联系人，如果是同步联系人，则不能修改BaseUserInfo
	/**
	 * 
	 * @param
	 */
	public ReturnType setUserInfo(UserInfo b);	
	public ReturnType setPermission(ID uid, Permission p);
	
	/**
	 * 建立或修改群组g
	 * @param g
	 * @return
	 */
	public ReturnType setGroup(Group g);
	/**
	 * 
	 * @param g
	 * @return
	 */
	public ReturnType removeGroup(Group g);
	
	/**
	 * 增加同步联系人关系
	 * @param uid 同步联系人的ID
	 * @return
	 */
	public ReturnType addSynRelationship(ID uid);
	
	/**
	 * 删除同步联系人关系
	 * @param uid 同步联系人的ID
	 * @return
	 */
	public ReturnType removeSynRelationship(ID uid);

	/**
	 * 增加被授权联系人关系，（从自己到uid用户)(add permitted relationship)
	 * @param uid 被授权联系人的ID
	 * @return
	 */
	public ReturnType addPerRelationship(ID uid);
	
	/**
	 * 删除被授权联系人关系
	 * @param uid 被授权联系人的ID
	 * @return
	 */
	public ReturnType removePerRelationship(ID uid);
	
	/**
	 * 导入文件
	 * @param fileName 文件名
	 * @return
	 */
	public ReturnType importFile(String fileName);
	
	/**
	 * 导出文件
	 * @param fileName 文件名
	 * @return
	 */
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
	public List<UserInfo> getAllUserInfo(LocalSynSource source);
}
