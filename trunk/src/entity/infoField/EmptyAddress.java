package entity.infoField;

/**
 * ��ַ�������������Ĺ��ҡ�ʡ���ʱ�Ƚṹ���Ե������������
 * ���䣬��ǰֻ����һ�����ַ������Ҹо����Ҫ����ַ���ֵ���ôϸ��
 * ��ôӦ�ø�ÿһ��ϸ�ڵ��ֶΣ�������ҡ�ʡ����һ��infoField������
 * �����Ͳ��ҡ�����һ�����Ʊ�ע���ʵ��ַ������ɡ�
 * 
 * �����ж����ַ���������⣬�ο�{@link EmptyCellphone}
 * @author Administrator
 *
 */
public class EmptyAddress extends EmptyInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7546082053813891890L;

	@Override
	public String getName() {
		return Messages.getString("Address"); //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return Address.maxLength;
	}
}
