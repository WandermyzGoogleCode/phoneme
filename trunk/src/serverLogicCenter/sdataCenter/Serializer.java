package serverLogicCenter.sdataCenter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;

public class Serializer {
	public static byte[] serialize(Object a){
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(a);
			oos.close();
			return baos.toByteArray();
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	public static Object unserialize(Blob blob){
		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(blob.getBytes(1, (int)blob.length()));
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}
}
