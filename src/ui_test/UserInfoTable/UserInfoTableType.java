package ui_test.UserInfoTable;

import entity.UserInfo;
import entity.infoField.InfoFieldName;
import entity.infoField.Relation;

public enum UserInfoTableType {
	/**
	 * δ֪
	 */
	Null,

	/**
	 * ��¼��
	 */
	Owner,

	/**
	 * ������ϵ�ˣ�RelationΪ�գ�
	 */
	Local,

	/**
	 * ͬ����ϵ��
	 */
	Synchronization,

	/**
	 * ����Ȩ��ϵ��
	 */
	Permission,

	/**
	 * �������
	 */
	SearchRemoteResult,

	/**
	 * ����������
	 */
	SearchLocalForm,

	/**
	 * Զ��������
	 */
	SearchRemoteForm,

	/**
	 * Ⱥ����ϵ��
	 */
	Group,

	/**
	 * ���û�ע��
	 */
	Register,

	/**
	 * ����±�����ϵ��
	 */
	NewLocal;

	public static UserInfoTableType getSynOrLocal(UserInfo user) {
		return ((Relation) user.getInfoField(InfoFieldName.Relation)).isEmpty() ? UserInfoTableType.Local
				: UserInfoTableType.Synchronization;
	}
}
