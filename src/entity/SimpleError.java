package entity;

public class SimpleError implements MyError {
	private String info;
	
	public SimpleError(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
