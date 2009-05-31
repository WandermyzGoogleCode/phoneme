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
	boolean nullFlag = true;

	static {
		try {
			encoder = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public Password(String value) {
		//TODO ºÏ≤È
		nullFlag = false;
		encodedValue = new String(encoder.digest(value.getBytes()));
	}

	public boolean isNull(){
		return nullFlag;
	}
	
	public String getEncodedValue() {
		return encodedValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Password)
			return encodedValue.equals(((Password)obj).encodedValue);
		else
			return false;
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
