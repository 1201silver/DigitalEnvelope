package wcs.signing;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManagement {
	
	static byte[] getData(StringBuffer filename) {
		
		String fn = filename.toString();
		
		byte[] data = new byte[128];
		
		try (FileInputStream fis = new FileInputStream(fn);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			
			fis.read(data);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	static void saveData(StringBuffer filename, byte[] data) {
		
		String fn = filename.toString();
		
		if(fn == null) {
			System.out.println("존재하지않는 " +fn+ " 입니다.");
		} else if(data == null) {
			System.out.println("data == null");
		}
		
		try(FileOutputStream fos = new FileOutputStream(fn)) {
			fos.write(data);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
