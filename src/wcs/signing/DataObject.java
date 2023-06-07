package wcs.signing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class DataObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private byte[] origindata;
	private byte[] signdata;
	private byte[] senderPublicKey;
	
	public DataObject() {
		
	}

	public DataObject(byte[] origindata, byte[] signdata, byte[] senderPublicKey) {
		super();
		this.origindata = origindata;
		this.signdata = signdata;
		this.senderPublicKey = senderPublicKey;
	}
	
	public byte[] getOrigindata() {
		return origindata;
	}

	public byte[] getSigndata() {
		return signdata;
	}

	public byte[] getSenderPublicKey() {
		return senderPublicKey;
	}

	// origindata, sign, senderPublicKey => Á÷·ÄÈ­
	protected byte[] serialization(DataObject d) {
		byte[] byteArr = null;
		
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
				oos.writeObject(d);
				byteArr = baos.toByteArray();
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return byteArr;
	}
	
	protected DataObject deserialization(byte[] byteArr) {
		DataObject d = new DataObject();
		
		try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArr)) {
			try (ObjectInputStream ois = new ObjectInputStream(bais)) {
				Object obj = ois.readObject();
				d = (DataObject) obj;
			
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return d;
	}
	
}
