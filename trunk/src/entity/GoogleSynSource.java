package entity;

import com.google.gdata.util.AuthenticationException;

import datacenter.google.GoogleContactOperator;

public class GoogleSynSource implements LocalSynSource {
	private static final String URLFormat = "http://www.google.com/m8/feeds/contacts/%s@gmail.com/full";
	private String username, pwd;
	private GoogleContactOperator operator = null;
	
	public GoogleSynSource(String username, String pwd){
		this.username = username;
		this.pwd = pwd;
	}
	
	public GoogleContactOperator getOperator() throws AuthenticationException{
		if (operator == null)
			operator = new GoogleContactOperator(this);
		return operator;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPostUrl(){
		return String.format(URLFormat, username);
	}
	
	@Override
	public String getName() {
		return "Google Contacts";
	}
}
