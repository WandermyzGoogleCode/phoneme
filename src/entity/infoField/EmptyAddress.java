package entity.infoField;

/**
 * 地址，至于里面具体的国家、省、邮编等结构可以到具体的类再做
 * 分配，当前只返回一个空字符串。我感觉如果要将地址划分到那么细，
 * 那么应该给每一个细节的字段（比如国家、省）开一个infoField，方便
 * 索引和查找。否则，一个类似备注性质的字符串即可。
 * 
 * 至于有多个地址的数组问题，参考{@link EmptyCellphone}
 * @author Administrator
 *
 */
public class EmptyAddress extends EmptyInfoField {

	@Override
	public String getName() {
		return "Address";
	}
}
