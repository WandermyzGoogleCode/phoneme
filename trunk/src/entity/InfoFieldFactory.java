package entity;

/**
 * ��������InfoField��FACTORY
 * ���������ַ�����name��value��FACTORY������name�жϽ���ʲô�����
 * InfoField
 * @author Administrator
 *
 */
public class InfoFieldFactory {
	public InfoField makeInfoField(String name, String value)
	{
		//SAMPLE
		if (name.equals("EmailAddress"))
		{
			return new EmailAddr(value);
		}
		//TODO
		return null;
	}
}
