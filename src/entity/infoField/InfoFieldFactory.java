package entity.infoField;
import java.util.ArrayList;
import java.util.List;

/**
 * ��������InfoField��FACTORY
 * ���������ַ�����name��value��FACTORY������name�жϽ���ʲô�����InfoField
 * 
 * Factory����Singleton�������Ƕ��ڴ����ĺ�������static�����Ա�
 * �Ժ����µ�Factory���Լ̳д�Factory��������Ӧ������������
 * @author Administrator
 *
 */
public class InfoFieldFactory {
	private static InfoFieldFactory factory = new InfoFieldFactory();
	
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
	
	private InfoFieldFactory(){
	}
	
	public List<EmptyInfoField> makeAllBaseEmptyField()
	{
		ArrayList<EmptyInfoField> res = new ArrayList<EmptyInfoField>();
		res.add(new EmptyAddress());
		res.add(new EmptyBirthday());
		res.add(new EmptyCellphone());
		res.add(new EmptyCompany());
		res.add(new EmptyEmailAddr());
		res.add(new EmptyImg());
		res.add(new EmptyName());
		res.add(new EmptyPhone());
		res.add(new EmptyPosition());
		res.add(new EmptyQQNumber());
		res.add(new EmptyWeb());
		return res;
	}
	
	public List<EmptyInfoField> makeAllCustomEmptyField()
	{
		ArrayList<EmptyInfoField> res = new ArrayList<EmptyInfoField>();
		res.add(new EmptyNickName());
		res.add(new EmptyRemarks());
		res.add(new EmptyTag());
		return res;		
	}
	
	public List<EmptyInfoField> makeAllGroupEmptyField()
	{
		ArrayList<EmptyInfoField> res = new ArrayList<EmptyInfoField>();
		res.add(new EmptyGroupName());
		res.add(new EmptyGroupDescription());
		res.add(new EmptyTag());
		return res;
	}
	public static InfoFieldFactory getFactory()
	{
		return factory;
	}
}
