package entity;

/**
 * 建立各种InfoField的FACTORY
 * 传入两个字符串：name和value，FACTORY来根据name判断建立什么具体的
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
