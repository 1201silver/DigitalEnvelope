package wcs.signing;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import wcs.key.KeyManagement;

public class DigitEnvelope {

	// 전자봉투 생성
	public void encryption(StringBuffer originDataFile, StringBuffer signDataFile,StringBuffer senderPublicFile, StringBuffer secretKeyFile, 
							StringBuffer receiverPublicFile, StringBuffer encryptObjFile, StringBuffer encryptEnvelopeFile) 
									throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		
		byte[] origindata = FileManagement.getData(originDataFile);
		byte[] signdata = FileManagement.getData(signDataFile);
		
		PublicKey senderPublicKey = (PublicKey) KeyManagement.getKey(senderPublicFile);
		PublicKey receiverPublicKey = (PublicKey) KeyManagement.getKey(receiverPublicFile);
		
		SecretKey secretKey = (SecretKey) KeyManagement.getKey(secretKeyFile);
		
		// origindata, signdata, senderPublicKey => 직렬화
		DataObject dataObj = new DataObject(origindata, signdata, senderPublicKey.getEncoded());
		byte[] objByte = dataObj.serialization(dataObj);
		System.out.println("\nsigndata len :" +signdata.length);
		if(objByte == null) {
			System.out.println("objByte == null");
		}
		
		// 직렬화한 객체 => secretKey로 암호화
		encryptSymmetric(encryptObjFile, secretKey, objByte);
		
		// secretKey => receiverPublicKey로 암호화 (encryptEnvelopeFile) (이게 전자봉투)
		encryptSymmetric(encryptEnvelopeFile, receiverPublicKey, secretKey);
	}
	
	// secretKey 사용해 암호화
	private void encryptSymmetric(StringBuffer filename, SecretKey secretKey, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		
		String fn = filename.toString();
		
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, secretKey);
		
		try (FileOutputStream fos = new FileOutputStream(fn);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				CipherOutputStream cos = new CipherOutputStream(fos, c)) {
			
			cos.write(data);
			cos.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// secretKey => receiverPublicKey로 암호화
	private void encryptSymmetric(StringBuffer filename, PublicKey receiverPublicKey, SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		
		String fn = filename.toString();
			
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, receiverPublicKey);
			
		try (FileOutputStream fos = new FileOutputStream(fn);
				CipherOutputStream cos = new CipherOutputStream(fos, c)) {
			
			cos.write(secretKey.getEncoded());
			cos.flush();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void decryption(StringBuffer encryptEnvelopeFile, StringBuffer encryptObjFile, StringBuffer receiverPrivateFile, StringBuffer senderPublicFile) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, SignatureException {
		
		// receiverPrivateKey로 encryptEnvelopeFile 복호화 => secretKey get
		PrivateKey receiverPrivateKey = (PrivateKey) KeyManagement.getKey(receiverPrivateFile);
		
		byte[] secretKeyByte = decryptAssymetric(encryptEnvelopeFile, receiverPrivateKey);
		System.out.println("");
		for(byte t: secretKeyByte) {
			System.out.print(String.format("%02x", t) +"  ");
		}
		System.out.println("");
		
		SecretKey secretKey = new SecretKeySpec(secretKeyByte, 0, secretKeyByte.length, "AES");
		for(byte t: secretKey.getEncoded()) {
			System.out.print(String.format("%02x", t) +"  ");
		}
		
		byte[] decryptObj = decryptAssymetric(encryptObjFile, secretKey);
		
		DataObject d = new DataObject();
		DataObject decryptDataObject = d.deserialization(decryptObj);
		
		byte[] signdata = decryptDataObject.getSigndata();
		System.out.println("\ndecrypt signdata");
		for(byte t: signdata) {
			System.out.print(String.format("%02x", t) +"  ");
		}
		
		DigitSign.verify(decryptDataObject.getOrigindata(), decryptDataObject.getSigndata(), senderPublicFile);
	}
	
	private byte[] decryptAssymetric(StringBuffer filename, PrivateKey receiverPrivateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		
		String fn = filename.toString();
		
		byte[] secretKey = null;
		
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.DECRYPT_MODE, receiverPrivateKey);
		
		try(FileInputStream fis = new FileInputStream(fn);
				CipherInputStream cis = new CipherInputStream(fis, c);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()){
			
				int len;
				while((len = cis.read()) != -1) {
					baos.write(len);
				}
				secretKey = baos.toByteArray();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return secretKey;
	}
	
	private byte[] decryptAssymetric(StringBuffer filename, SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		
		String fn = filename.toString();
		
		byte[] decryptObj = null;
		
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, secretKey);
		
		try(FileInputStream fis = new FileInputStream(fn);
				CipherInputStream cis = new CipherInputStream(fis, c);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()){
			
				int len;
				while((len = cis.read()) != -1) {
					baos.write(len);
				}
				decryptObj = baos.toByteArray();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return decryptObj;
	}
}
