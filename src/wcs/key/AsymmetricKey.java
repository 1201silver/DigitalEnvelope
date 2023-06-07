package wcs.key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AsymmetricKey { // 비대칭 키 생성, 저장
	
	private KeyPairGenerator keyPairGen;
	private KeyPair keyPair;
	
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	// getter, setter 이용하려면 AsymmetricKey 객체 필요
	static AsymmetricKey getInstance() throws NoSuchAlgorithmException {
		
		AsymmetricKey ak = new AsymmetricKey();
		
		ak.keyPairGen = KeyPairGenerator.getInstance("RSA");
		ak.keyPairGen.initialize(1024);
		
		return ak;
	}
	
	void generateKey() {
		keyPair = keyPairGen.generateKeyPair();

		publicKey = keyPair.getPublic();
		privateKey = keyPair.getPrivate();
	}
	
	PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	PublicKey getPublicKey() {
		return publicKey;
	}

}
