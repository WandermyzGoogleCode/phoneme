package datacenter;
import java.util.List;

import entity.*;
public interface DataCenter {
	//�ж�һЩ�Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo
	/**
	 * 
	 * @param
	 */
	public ReturnType setUserInfo(UserInfo b);
	
	/**
	 * ������ǰ�û���id��Ȩ�ޣ�id������Ⱥ���id��Ҳ�������û���id
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
	 * ���õ�ǰ�û���uid�û��Ĺ�ϵ�ɼ��ԡ�uid�ǵ�ǰ�û���ͬ����ϵ�ˡ�
	 * @param uid
	 * @param visibility
	 * @return
	 */
	public ReturnType setVisibility(ID uid, int visibility);
	
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
	 * 
	 * @param source ��ȡ�û���Ϣ��;��������ֱ�Ӵ��Լ������ݿ⣬���ߴ�Outlook��Google Synchronized�е�
	 * @return
	 */
	public List<UserInfo> getAllUserInfo(LocalSynSource source);
	
	public ReturnType removeUserInfo(ID uid);
	
	//TODO �������
	/**
	 * ��ȡ���б���Ȩ��ϵ�˵�ID
	 * @return
	 */
	public List<ID> getAllPerContactsID();
	
	//TODO �������
	/**
	 * ��ȡ����ͬ����ϵ�˵�ID
	 * @return
	 */
	public List<ID> getAllSynContactsID();
}
