package wcs.signing;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import wcs.key.KeyManagement;

public class DigitSign {
	
	// /sender의 privateKey로 전자서명 생성
	public static void sign(StringBuffer origindataFile, StringBuffer senderPrivateFile, StringBuffer signfile) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		byte[] origindata = FileManagement.getData(origindataFile);

		PrivateKey privateKey = (PrivateKey) KeyManagement.getKey(senderPrivateFile);
		
		Signature sig = Signature.getInstance("SHA256withRSA");
		sig.initSign(privateKey);
		sig.update(origindata);
		byte[] signdata = sig.sign();
		
		FileManagement.saveData(signfile, signdata);
	}
	
	static void verify(byte[] origindata, byte[] signdata, StringBuffer senderPublicFile) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		PublicKey publicKey = (PublicKey) KeyManagement.getKey(senderPublicFile);

		Signature sig = Signature.getInstance("SHA256withRSA");
		sig.initVerify(publicKey);
		sig.update(origindata);
		
		System.out.println("\n생성된 서명 정보: " +signdata.length+ " bytes");
		for(byte b: signdata) {
			System.out.print(String.format("%02x", b) +"  ");
		}
		
		boolean result = sig.verify(signdata);
		System.out.println("\n서명 검증 결과: " +result);
	}

}
