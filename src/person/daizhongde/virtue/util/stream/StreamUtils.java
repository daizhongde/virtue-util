package person.daizhongde.virtue.util.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	
	/**
	 * 
	 * @Description:  InputStream转byte[] 
	 * 
	 * @param: @param is
	 * @param: @return
	 * @param: @throws IOException      
	 * @return: byte[]      
	 * @throws
	 */
	public static byte[] convertingAnInputStreamToAByteArray(InputStream is) throws IOException{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    int nRead;
	    byte[] data = new byte[1024];
	    while ((nRead = is.read(data, 0, data.length)) != -1) {
	        buffer.write(data, 0, nRead);
	    }
	 
	    buffer.flush();
	    byte[] byteArray = buffer.toByteArray();
	    
		try {
			if(is!=null ){ 	is.close(); }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    return byteArray;
	}

	/**
	 * 
	 * @Description:   File 转byte[] 
	 * 
	 * @param: @param file
	 * @param: @return
	 * @param: @throws IOException      
	 * @return: byte[]      
	 * @throws
	 */
	public static byte[] convertingFileToAByteArray(File file) throws IOException{
		 
		 FileInputStream fis = new FileInputStream(file);
		 ByteArrayOutputStream baos = new ByteArrayOutputStream(fis.available());
		 byte[] bytes = new byte[fis.available()];
		 int temp;
		 while ((temp = fis.read(bytes)) != -1) {
		     baos.write(bytes, 0, temp);
		 }
		 fis.close();
		 baos.close();
		 return baos.toByteArray();
	}
	
	/**
	 * 
	 * @Description: byte[] 转 InputStream
	 * 
	 * @param: @param bytes
	 * @param: @return
	 * @param: @throws IOException      
	 * @return: InputStream      
	 * @throws
	 */
	public static InputStream convertingAByteArrayToAnInputStream( byte[]  bytes) throws IOException{
	    return new ByteArrayInputStream(bytes);
	}
	
	
}
