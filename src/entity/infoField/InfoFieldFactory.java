package entity.infoField;
import java.util.ArrayList;
import java.util.List;

/**
 * 建立各种InfoField的FACTORY
 * 传入两个字符串：name和value，FACTORY来根据name判断建立什么具体的InfoField
 * 
 * Factory采用Singleton，而不是对于创建的函数进行static化，以便
 * 以后有新的Factory可以继承此Factory并重载相应的生产函数。
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
