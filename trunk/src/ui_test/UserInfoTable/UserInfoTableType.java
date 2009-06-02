package ui_test.UserInfoTable;

public enum UserInfoTableType
{
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
	SearchResult,
	
	/**
	 * 搜索表单
	 */
	SearchForm,
	
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
	NewLocal,
}
