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
}
