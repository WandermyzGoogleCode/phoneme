package entity.infoField;

import java.util.HashMap;
import java.util.Map;

/**
 * ��չ���ݣ��������ݿ�����棬ֻ��������ʱ
 * �洢һЩ������
 * @author Administrator
 *
 */
public class Extension extends EmptyInfoField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> values = new HashMap<String, String>();
	
	public void setValue(String key, String value){
		values.put(key, value);
	}
	
	public String getValue(String key){
		return values.get(key);
	}

	@Override
	public int getMaxLength() {
		return 0;
	}

	@Override
	public String getName() {
		return "Extension";
	}

	/**
	 * ���ɱ༭
	 */
	@Override
	public boolean editable() {
		return false;
	}
	
	/**
	 * ������ʾ�����Ƿ��ؿ��ַ���
	 */
	@Override
	public boolean visible() {
		return false;
	}
	
	/**
	 * ���ɴ洢�������Կ��ַ����������ݿ�
	 */
	@Override
	public boolean savable() {
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return values.isEmpty();
	}
}
