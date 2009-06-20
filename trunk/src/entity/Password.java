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
			encoder = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString()); //$NON-NLS-1$
			e.printStackTrace();
		}
	}

	public Password(String value) {
		if (value.equals("")) //$NON-NLS-1$
			return;//不合法
		//TODO 更详细的检查
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
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Password a = new Password("winwinwin"), b = new Password("Win"), c = new Password( //$NON-NLS-1$ //$NON-NLS-2$
				"win"); //$NON-NLS-1$
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
			System.out.println("你好".length()); //$NON-NLS-1$
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
