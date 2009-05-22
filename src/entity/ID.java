package entity;

import java.io.Serializable;

/**
 * 用来标识用户或者群组的唯一标志，只有系统可见，用户不可见
 * 该ID由系统的IDFactory自动生成，
 * @author Administrator
 *
 */
public class ID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3866497586725147310L;
	private int id;
	
	static ID getNullID()
	{
		return new ID(-1);
	}
	
	public ID(int id)
	{
		this.id = id;
	}
	
	public int getValue(){
		return id;
	}
}
