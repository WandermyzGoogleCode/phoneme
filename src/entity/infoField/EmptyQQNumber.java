package entity.infoField;

/**
 * QQ这个IM的infoField
 * 其它的IM再说，反正都类似
 * @author Administrator
 *
 */
public class EmptyQQNumber extends EmptyInfoField implements IdenticalInfoField {

	@Override
	public String getName() {
		return "QQNumber";
	}

}
