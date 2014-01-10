package egovframework.eam.api.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {
	public static byte[] getByteArrayFrom(Object obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(obj);
			oos.close();

			return baos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Object getObjectFrom(byte[] data) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bais);

			return ois.readObject();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
