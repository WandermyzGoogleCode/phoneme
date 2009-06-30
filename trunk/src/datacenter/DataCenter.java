package datacenter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import entity.*;
public interface DataCenter {
	//判断一些是否是同步联系人，如果是同步联系人，则不能修改BaseUserInfo
	/**
	 * 
	 * @param
	 */
	public ReturnType setUserInfo(UserInfo b);
	
	/**
	 * 已经废弃，因为当前没有读到接口，所以所有权限都直接去网上弄了
	 * @param id
	 * @param p
	 * @return
	 */
	public ReturnType setPermission(ID id, Permission p);
	
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
	 * @param fileName 文件名，带路径
	 * @return
	 */
	public ReturnType importFile(String fileName);
	
	/**
	 * 导出文件
	 * @param fileName 文件名，带路径
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
	 * source为null时，直接从数据库拿。
	 * 
	 * @param source 获取用户信息的途径。比如直接从自己的数据库，或者从Outlook，Google Synchronized中等
	 * @return
	 * @throws AuthenticationException 
	 * @throws IOException 
	 * @throws ServiceException 
	 * @throws MalformedURLException 
	 * @throws SQLException 
	 */
	public List<UserInfo> getAllUserInfo(LocalSynSource source) throws AuthenticationException, MalformedURLException, ServiceException, IOException, SQLException;
	
	public ReturnType removeUserInfo(ID uid);
	
	/**
	 * 获取所有被授权联系人的ID
	 * @return
	 */
	public List<ID> getAllPerContactsID();
	
	/**
	 * 获取所有同步联系人的ID
	 * @return
	 */
	public List<ID> getAllSynContactsID();
	
	/**
	 * 获取所有已经加入的群组
	 */
	public List<Group> getAllGroups();

	/**
	 * 更具users中Extension字段包含的操作信息，
	 * 更新source同步源中的联系人数据
	 * @param users
	 * @param source
	 * @throws AuthenticationException 
	 * @throws ServiceException 
	 * @throws IOException 
	 */
	public void updateSource(List<UserInfo> users, LocalSynSource source) throws AuthenticationException, IOException, ServiceException;
}
