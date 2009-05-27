package entity;

import java.io.Serializable;

/**
 * ��¼����ٺ����˵�������ͣ����ڼ�¼�����ĳɹ�����Ѿ������Ϣ��
 * Ĭ�Ϲ���ʱ�����ؽ��Ϊ�档
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
	
	public BoolInfo(MyError err) {
		bool = false;
		this.info = err.toString();
	}
	
	public BoolInfo(String errInfo){
		bool = false;
		this.info = errInfo;
	}
	
	public BoolInfo(){}
	
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
