package entity.infoField;

/**
 * ���ǾͲ�ʵ��ʵ�ʺ���������ˣ�
 * ��Ϊ��������鷳�������ڵ����Ҳ��������֧�֡�
 * ������Ҫ�����ǿ����Ƴ�BaseUserInfo2.0, 3.0֮��ģ�
 * Ȼ�����������"Cellphone2", "Cellphone3"֮����ֶ�
 * @author Administrator
 *
 */
public abstract class EmptyCellphone extends EmptyInfoField implements IdenticalInfoField 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5463180936829136629L;

	@Override
	public String getName() {
		return "Cellphone";
	}

	@Override
	public int getMaxLength() {
		return Cellphone.maxLength;
	}
}
