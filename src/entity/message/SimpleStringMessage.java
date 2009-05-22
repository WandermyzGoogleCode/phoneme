package entity.message;



public class SimpleStringMessage implements Message {
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
