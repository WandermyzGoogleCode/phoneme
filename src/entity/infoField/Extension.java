package entity.infoField;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展数据，不往数据库里面存，只是用来临时
 * 存储一些东西。
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
	 * 不可编辑
	 */
	@Override
	public boolean editable() {
		return false;
	}
	
	/**
	 * 不可显示，总是返回空字符串
	 */
	@Override
	public boolean visible() {
		return false;
	}
	
	/**
	 * 不可存储，总是以空字符串存入数据库
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
