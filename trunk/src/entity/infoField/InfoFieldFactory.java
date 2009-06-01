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
	
	/**
	 * Ϊ�˷����½�InfoField��ʱ���������֣�����֤��ȫ�ԣ�
	 * �����˸ú����Դ���ֱ����String�������֡��ɵĴ����������á�
	 * @param name
	 * @param value
	 * @return
	 */
	public InfoField makeInfoField(InfoFieldName name, String value){
		return makeInfoField(name.name(), value);
	}
	
	public InfoField makeInfoField(String name, String value)
	{
		if (name.equals("EmailAddress"))
			return new EmailAddr(value);
		if (name.equals("Cellphone"))
			return new Cellphone(value);
		//TODO �����infoField
		if (name.equals("Address"))
		{
			return new Address(value);
		}
		if (name.equals("Birthday"))
		{
			return new Birthday(value);
		}
		if (name.equals("Cellphone"))
		{
			return new Cellphone(value);
		}		
		if (name.equals("Company"))
		{
			return new Company(value);
		}
		if (name.equals("GroupDescription"))
		{
			return new GroupDescription(value);
		}
		if (name.equals("GroupName"))
		{
			return new GroupName(value);
		}
		if (name.equals("Image"))
		{
			return new Img(value);
		}
		if (name.equals("Name"))
		{
			return new Name(value);
		}
		if (name.equals("NickName"))
		{
			return new NickName(value);
		}
		if (name.equals("Phone"))
		{
			return new Phone(value);
		}
		if (name.equals("Position"))
		{
			return new Position(value);
		}
		if (name.equals("QQNumber"))
		{
			return new QQNumber(value);
		}
		if (name.equals("Remarks"))
		{
			return new Remarks(value);
		}
		if (name.equals("Category"))
		{
			return new Tag(value);
		}
		if (name.equals("Website"))
		{
			return new Web(value);
		}
		if (name.equals("Relation"))
			return new Relation(value);
		return null;
	}
	
	private InfoFieldFactory(){
	}
	
	public List<InfoField> makeAllBaseEmptyField()
	{
		ArrayList<InfoField> res = new ArrayList<InfoField>();
		for(BaseInfoFieldName name: BaseInfoFieldName.values())
			res.add(makeInfoField(name.name(), ""));
		return res;
	}
	
	public List<InfoField> makeAllCustomEmptyField()
	{
		ArrayList<InfoField> res = new ArrayList<InfoField>();
		for(CustomInfoFieldName name: CustomInfoFieldName.values())
			res.add(makeInfoField(name.name(), ""));
		return res;
	}
	
	public List<InfoField> makeAllGroupEmptyField()
	{
		ArrayList<InfoField> res = new ArrayList<InfoField>();
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
