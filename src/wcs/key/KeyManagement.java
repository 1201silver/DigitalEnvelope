package wcs.key;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyManagement { // 키 관리 (getKey)
	
	public static void genAsymmetricKey(StringBuffer privateFile, StringBuffer publicFile) throws NoSuchAlgorithmException {
		
		AsymmetricKey ak;
		ak = AsymmetricKey.getInstance();
		ak.generateKey();
		
		KeyManagement.saveKeyinFile(ak.getPrivateKey(), privateFile);
		KeyManagement.saveKeyinFile(ak.getPublicKey(), publicFile);
		
		System.out.println("키 생성 완료");
	}
	
	public static void genSymmetricKey(StringBuffer secretFile) throws NoSuchAlgorithmException {
		
		SymmetricKey sk;
		sk = SymmetricKey.getInstance();
		sk.generateKey();
		
		KeyManagement.saveKeyinFile(sk.getSecretKey(), secretFile);
		
		System.out.println("키 생성 완료");
	}
	
	static void saveKeyinFile(Key key, StringBuffer filename) {
		
		String fn = filename.toString();
		
		try (FileOutputStream fstream = new FileOutputStream(fn)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(key);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// key 불러오기
	public static Key getKey(StringBuffer filename) {
		
		String fn = filename.toString();

		Key key = null;

		try (FileInputStream fis = new FileInputStream(fn)) {
			try (ObjectInputStream ois = new ObjectInputStream(fis)) {

				Object obj = ois.readObject();
				key = (Key) obj;

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return key;
	}
}
