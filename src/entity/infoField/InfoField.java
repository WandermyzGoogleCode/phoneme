package entity.infoField;

import java.io.Serializable;

/**
 * UserInfo�У�����BaseUserInfo��CustomUserInfo)���ֶε���߲�ӿڡ�
 * �������û��ֶο��ֻܷ�������������ֶΣ�IdenticalInfoField, IndexedInfoField��������������ӿڵ�˵����
 * UserInfoֻ��һ��MAP<fieldName:String, value:InfoField>����¼��Щ�ֶΣ��������ǵ����ֵ�ֵ��������
 * ����"name" -> "SpaceFlyer"����ͨ��get(fieldName:String)�����������ֶΡ�
 * 
 * ע�⣬�����ṩ��fieldNameֻ�ǹ������ڲ��ο������ճ��ָ��û�ʱ��Ӧ�ü�һ��
 * external�����������ļ�������һ��fieldName����ʾ�����ֵ�ӳ�䡣
 * @author Administrator
 *
 */
public interface InfoField extends Serializable{
	/**
	 * 
	 * @return ���ֶε�����
	 */
	public String getName();
	
	/**
	 * ����Ƕ�ý��������ֻ����һ��URL��������ʾʲô���ϲ������
	 * @return ���ֶε�ֵ
	 */
	public String getStringValue();
	
	public boolean isEmpty();
	
	/**
	 * getStringValue����󳤶�
	 * @return
	 */
	public int getMaxLength();
}
