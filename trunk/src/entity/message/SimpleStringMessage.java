package entity.message;

import entity.ID;
import logiccenter.LogicCenter;



public class SimpleStringMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1934507647581023984L;
	private String str;
	
	@Override
	public String toString()
	{
		return str;
	}
	
	public SimpleStringMessage(String str, ID id)
	{
		super(id);
		this.str = str;
	}
	
	@Override
	public void proceed(LogicCenter center) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public String title() {
		return str;
	}
	
	@Override
	public String detail() {
		return str;
	}
}