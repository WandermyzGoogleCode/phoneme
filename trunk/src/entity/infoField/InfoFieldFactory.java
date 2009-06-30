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
		if (name.equals(Messages.getString("EmailAddress"))) //$NON-NLS-1$
			return new EmailAddr(value);
		if (name.equals(Messages.getString("Cellphone"))) //$NON-NLS-1$
			return new Cellphone(value);
		//TODO �����infoField
		if (name.equals(Messages.getString("Address"))) //$NON-NLS-1$
		{
			return new Address(value);
		}
		if (name.equals(Messages.getString("Birthday"))) //$NON-NLS-1$
		{
			return new Birthday(value);
		}
		if (name.equals(Messages.getString("Cellphone"))) //$NON-NLS-1$
		{
			return new Cellphone(value);
		}		
		if (name.equals(Messages.getString("Company"))) //$NON-NLS-1$
		{
			return new Company(value);
		}
		if (name.equals(Messages.getString("GroupDescription"))) //$NON-NLS-1$
		{
			return new GroupDescription(value);
		}
		if (name.equals(Messages.getString("GroupName"))) //$NON-NLS-1$
		{
			return new GroupName(value);
		}
		if (name.equals(Messages.getString("Image"))) //$NON-NLS-1$
		{
			return new Img(value);
		}
		if (name.equals(Messages.getString("Name"))) //$NON-NLS-1$
		{
			return new Name(value);
		}
		if (name.equals(Messages.getString("NickName"))) //$NON-NLS-1$
		{
			return new NickName(value);
		}
		if (name.equals(Messages.getString("Phone"))) //$NON-NLS-1$
		{
			return new Phone(value);
		}
		if (name.equals(Messages.getString("Position"))) //$NON-NLS-1$
		{
			return new Position(value);
		}
		if (name.equals(Messages.getString("QQNumber"))) //$NON-NLS-1$
		{
			return new QQNumber(value);
		}
		if (name.equals(Messages.getString("Remarks"))) //$NON-NLS-1$
		{
			return new Remarks(value);
		}
		if (name.equals(Messages.getString("Category"))) //$NON-NLS-1$
		{
			return new Tag(value);
		}
		if (name.equals(Messages.getString("Website"))) //$NON-NLS-1$
		{
			return new Web(value);
		}
		if (name.equals(Messages.getString("Relation"))) //$NON-NLS-1$
			return new Relation(value);
		if (name.equals("Extension"))
			return new Extension();
		return null;
	}
	
	private InfoFieldFactory(){
	}
	
	public List<InfoField> makeAllBaseEmptyField()
	{
		ArrayList<InfoField> res = new ArrayList<InfoField>();
		for(BaseInfoFieldName name: BaseInfoFieldName.values())
			res.add(makeInfoField(name.name(), "")); //$NON-NLS-1$
		return res;
	}
	
	public List<InfoField> makeAllCustomEmptyField()
	{
		ArrayList<InfoField> res = new ArrayList<InfoField>();
		for(CustomInfoFieldName name: CustomInfoFieldName.values())
			res.add(makeInfoField(name.name(), "")); //$NON-NLS-1$
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
