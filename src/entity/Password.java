package entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		Password a = new Password("winwinwin"), b = new Password("Win"), c = new Password(
				"win");
		System.out.println(a.getEncodedValue());
		System.out.println(b.getEncodedValue());
		System.out.println(c.getEncodedValue());
		System.out.println(a.equals(b));
		System.out.println(a.equals(c));
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(a);
			oos.close();
			String str = new String(baos.toByteArray());
			System.out.println(str.length());
			System.out.println(str);
			System.out.println(baos.toByteArray().length);
			System.out.println(baos.toByteArray());
			System.out.println(str.getBytes().length);
			System.out.println(str.getBytes());
			System.out.println("ƒ„∫√".length());
			ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
			ObjectInputStream ois = new ObjectInputStream(bais);
			Password ra = (Password)ois.readObject();
			System.out.println(ra.equals(a));

			/*FileOutputStream baos = new FileOutputStream("test.file");
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(a);
			oos.close();
			String str = baos.toString();
			System.out.println(str.length());
			System.out.println(str);
			FileInputStream bais = new FileInputStream("test.file");
			ObjectInputStream ois = new ObjectInputStream(bais);
			Password ra = (Password)ois.readObject();
			System.out.println(ra.equals(a));*/
}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
