package entity.message;



public class SimpleStringMessage implements Message {
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
	
	public SimpleStringMessage(String str)
	{
		this.str = str;
	}
}
