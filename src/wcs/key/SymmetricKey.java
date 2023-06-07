package wcs.key;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

public class SymmetricKey { // ��Ī Ű ����, ����
	
	private KeyGenerator keyGen;
	private Key secretKey;
	
	static SymmetricKey getInstance() throws NoSuchAlgorithmException {
		SymmetricKey sk = new SymmetricKey();
		
		sk.keyGen = KeyGenerator.getInstance("AES");
		sk.keyGen.init(128);
		
		return sk;
	}
	
	void generateKey() {
		secretKey = keyGen.generateKey();
	}
	
	Key getSecretKey() {
		return secretKey;
	}

}
