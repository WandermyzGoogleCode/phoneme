package entity;

import java.io.Serializable;
import java.security.MessageDigest;

public class Password implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4823009801317584895L;
	private static MessageDigest encoder;
	String encodedValue;

	static {
		try {
			encoder = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public Password(String value) {
		encodedValue = new String(encoder.digest(value.getBytes()));
	}

	public String getEncodedValue() {
		return encodedValue;
	}

	public boolean equals(Password obj) {
		return encodedValue.equals(obj.encodedValue);
	}
	
	public void setNewPassword(String value) {
		encodedValue = new String(encoder.digest(value.getBytes()));
	}

	/**
	 * ≤‚ ‘
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Password a = new Password("win"), b = new Password("Win"), c = new Password(
				"win");
		System.out.println(a.getEncodedValue());
		System.out.println(b.getEncodedValue());
		System.out.println(c.getEncodedValue());
		System.out.println(a.equals(b));
		System.out.println(a.equals(c));
	}
}
