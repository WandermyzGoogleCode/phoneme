package datacenter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import entity.*;
public interface DataCenter {
	//�ж�һЩ�Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo
	/**
	 * 
	 * @param
	 */
	public ReturnType setUserInfo(UserInfo b);
	
	/**
	 * �Ѿ���������Ϊ��ǰû�ж����ӿڣ���������Ȩ�޶�ֱ��ȥ����Ū��
	 * @param id
	 * @param p
	 * @return
	 */
	public ReturnType setPermission(ID id, Permission p);
	
	/**
	 * �������޸�Ⱥ��g
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
	 * ����ͬ����ϵ�˹�ϵ
	 * @param uid ͬ����ϵ�˵�ID
	 * @return
	 */
	public ReturnType addSynRelationship(ID uid);
	
	/**
	 * ɾ��ͬ����ϵ�˹�ϵ
	 * @param uid ͬ����ϵ�˵�ID
	 * @return
	 */
	public ReturnType removeSynRelationship(ID uid);

	/**
	 * ���ӱ���Ȩ��ϵ�˹�ϵ�������Լ���uid�û�)(add permitted relationship)
	 * @param uid ����Ȩ��ϵ�˵�ID
	 * @return
	 */
	public ReturnType addPerRelationship(ID uid);
	
	/**
	 * ɾ������Ȩ��ϵ�˹�ϵ
	 * @param uid ����Ȩ��ϵ�˵�ID
	 * @return
	 */
	public ReturnType removePerRelationship(ID uid);
	
	/**
	 * �����ļ�
	 * @param fileName �ļ�������·��
	 * @return
	 */
	public ReturnType importFile(String fileName);
	
	/**
	 * �����ļ�
	 * @param fileName �ļ�������·��
	 * @return
	 */
	public ReturnType exportFile(String fileName);

	/**
	 * ���û�uid���뵽Ⱥ��g��
	 * @param g
	 * @param uid
	 * @return
	 */
	public ReturnType addToGroup(Group g, ID uid);
	
	/**
	 * ���û�uid��Ⱥ��g��ɾ��
	 * @param g
	 * @param uid
	 * @return
	 */
	public ReturnType removeFromGroup(Group g, ID uid);

	/***
	 * sourceΪnullʱ��ֱ�Ӵ����ݿ��á�
	 * 
	 * @param source ��ȡ�û���Ϣ��;��������ֱ�Ӵ��Լ������ݿ⣬���ߴ�Outlook��Google Synchronized�е�
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
	 * ��ȡ���б���Ȩ��ϵ�˵�ID
	 * @return
	 */
	public List<ID> getAllPerContactsID();
	
	/**
	 * ��ȡ����ͬ����ϵ�˵�ID
	 * @return
	 */
	public List<ID> getAllSynContactsID();
	
	/**
	 * ��ȡ�����Ѿ������Ⱥ��
	 */
	public List<Group> getAllGroups();

	/**
	 * ����users��Extension�ֶΰ����Ĳ�����Ϣ��
	 * ����sourceͬ��Դ�е���ϵ������
	 * @param users
	 * @param source
	 * @throws AuthenticationException 
	 * @throws ServiceException 
	 * @throws IOException 
	 */
	public void updateSource(List<UserInfo> users, LocalSynSource source) throws AuthenticationException, IOException, ServiceException;
}
