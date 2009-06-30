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
		return ""; //$NON-NLS-1$
	}
	
	/**
	 * Ĭ��Ϊ�棬ĳЩ��������²�Ϊ�٣�����NoName, 1111-11-11����Щ����
	 * ��Ȼ��������Ϊ�գ�������ʵ����Ч�ġ�
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public String toString() {
		return getStringValue();
	}
	
	/**
	 * Ĭ�϶��ܱ��༭
	 */
	@Override
	public boolean editable() {
		return true;
	}
	
	/**
	 * Ĭ�϶��ܱ���ʾ
	 */
	@Override
	public boolean visible() {
		return true;
	}
	
	/**
	 * Ĭ�϶��ܱ��洢
	 */
	@Override
	public boolean savable() {
		return true;
	}
}
