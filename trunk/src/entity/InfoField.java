package entity;

/**
 * UserInfo中（包括BaseUserInfo和CustomUserInfo)的字段的最高层接口。
 * 接下来用户字段可能分化出两个特殊的字段：IdenticalInfoField, IndexedInfoField，具体见这两个接口的说明。
 * UserInfo只用一个MAP<fieldName:String, value:InfoField>来记录这些字段，建立他们的名字到值的索引，
 * 比如"name" -> "SpaceFlyer"，并通过get(fieldName:String)来向外界输出字段。
 * @author Administrator
 *
 */
public interface InfoField {
	/**
	 * 
	 * @return 该字段的名字
	 */
	public String getName();
	
	/**
	 * 如果是多媒体流，就只返回一个URL。具体显示什么由上层决定。
	 * @return 该字段的值
	 */
	public String getStringValue();
}
