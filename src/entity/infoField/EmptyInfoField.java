package entity.infoField;

/**
 * �յ��ֶΣ������κθ�ʽ��飬ֻ���½�һ���յ�UserInfo��ʱ��ʹ�á����о����ֶζ����ʱ��
 * ���ֶμ����滻��
 * @author Administrator
 *
 */
public abstract class EmptyInfoField implements InfoField {
	@Override
	public String getStringValue() {
		return new String();
	}
}
