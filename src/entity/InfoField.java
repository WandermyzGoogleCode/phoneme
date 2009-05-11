package entity;

/**
 * UserInfo�У�����BaseUserInfo��CustomUserInfo)���ֶε���߲�ӿڡ�
 * �������û��ֶο��ֻܷ�������������ֶΣ�IdenticalInfoField, IndexedInfoField��������������ӿڵ�˵����
 * UserInfoֻ��һ��MAP<fieldName:String, value:InfoField>����¼��Щ�ֶΣ��������ǵ����ֵ�ֵ��������
 * ����"name" -> "SpaceFlyer"����ͨ��get(fieldName:String)�����������ֶΡ�
 * @author Administrator
 *
 */
public interface InfoField {
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
}
