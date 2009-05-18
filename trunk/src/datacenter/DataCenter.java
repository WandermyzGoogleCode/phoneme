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
	public ReturnType setPermission(ID uid, Permission p);
	
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
	 * @param fileName �ļ���
	 * @return
	 */
	public ReturnType importFile(String fileName);
	
	/**
	 * �����ļ�
	 * @param fileName �ļ���
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
}
