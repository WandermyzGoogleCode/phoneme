package entity.infoField;

/**
 * ���ǾͲ�ʵ��ʵ�ʺ���������ˣ�
 * ��Ϊ��������鷳�������ڵ����Ҳ��������֧�֡�
 * ������Ҫ�����ǿ����Ƴ�BaseUserInfo2.0, 3.0֮��ģ�
 * Ȼ�����������"CellPhone2", "CellPhone3"֮����ֶ�
 * @author Administrator
 *
 */
public class EmptyCellphone extends EmptyInfoField implements IdenticalInfoField 
{

	@Override
	public String getName() {
		return "CellPhone";
	}
}
