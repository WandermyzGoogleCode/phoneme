package entity.infoField;

/**
 * �յ��ֶΣ������κθ�ʽ��飬ֻ���½�һ���յ�UserInfo��ʱ��ʹ�á����о����ֶζ����ʱ��
 * ���ֶμ����滻��
 * @author Administrator
 *
 */
public abstract class EmptyInfoField implements InfoField {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8371556469263751670L;

	@Override
	public String getStringValue() {
		return "";
	}
	
	/**
	 * Ĭ��Ϊ�棬ĳЩ��������²�Ϊ�٣�����NoName, 1111-11-11����Щ����
	 * ��Ȼ��������Ϊ�գ�������ʵ����Ч�ġ�
	 */
	@Override
	public boolean isValid() {
		return true;
	}
}
