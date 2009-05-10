package entity;

/**
 * UserInfo中（包括BaseUserInfo和CustomUserInfo)的字段的最高层接口。
 * 接下来用户字段可能分化出两个特殊的字段：IdenticalInfoField, IndexedInfoField，具体见这两个接口的说明。
 * UserInfo只用一个MAP<fieldName:String, value:InfoField>来记录这些字段，建立他们的名字到值的索引，比如"name" -> "SpaceFlyer"，并通过get(fieldName:String)
 * 来向外界输出字段。
 * @author Administrator
 *
 */
public interface InfoField {

}
