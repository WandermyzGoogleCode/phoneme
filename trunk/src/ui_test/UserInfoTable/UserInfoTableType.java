package ui_test.UserInfoTable;

import entity.UserInfo;
import entity.infoField.InfoFieldName;
import entity.infoField.Relation;

public enum UserInfoTableType {
	/**
	 * 未知
	 */
	Null,

	/**
	 * 登录者
	 */
	Owner,

	/**
	 * 本地联系人（Relation为空）
	 */
	Local,

	/**
	 * 同步联系人
	 */
	Synchronization,

	/**
	 * 被授权联系人
	 */
	Permission,

	/**
	 * 搜索结果
	 */
	SearchRemoteResult,

	/**
	 * 本地搜索表单
	 */
	SearchLocalForm,

	/**
	 * 远程搜索表单
	 */
	SearchRemoteForm,

	/**
	 * 群组联系人
	 */
	Group,

	/**
	 * 新用户注册
	 */
	Register,

	/**
	 * 添加新本地联系人
	 */
	NewLocal;

	public static UserInfoTableType getSynOrLocal(UserInfo user) {
		return ((Relation) user.getInfoField(InfoFieldName.Relation)).isEmpty() ? UserInfoTableType.Local
				: UserInfoTableType.Synchronization;
	}
}
