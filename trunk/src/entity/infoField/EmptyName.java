package entity.infoField;

/**
 * ȫ����first name��second name�İ������
 * �ھ����Name�����У������й�NAME,XX��NAME������һ��STRATEGY��
 * ���������ֵķָȻ���ṩgetFirstName��getLastName�Ľӿڡ�
 * @author Administrator
 *
 */
public class EmptyName extends EmptyInfoField implements IndexedInfoField 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4622220009761467875L;

	@Override
	public String getName() {
		return "Name";
	}

	@Override
	public int getMaxLength() {
		return Name.maxLength;
	}

}
