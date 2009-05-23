package entity;

import java.io.Serializable;

/**
 * 记录了真假和相关说明的类型，用于记录操作的成功与否，已经相关信息。
 * 默认构造时，返回结果为真。
 * @author Administrator
 *
 */
public class BoolInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -108515655303371172L;
	private boolean bool = true;
	private String info = "Default is true";
	
	public boolean isTrue(){
		return bool;
	}
	public String getInfo(){
		return info;
	}
	
	public void setBool(boolean bool){
		this.bool = bool;
	}
	
	public void setInfo(String info){
		this.info = info;
	}
}
