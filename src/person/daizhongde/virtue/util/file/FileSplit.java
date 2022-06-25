package person.daizhongde.virtue.util.file;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

public class FileSplit {

	public static void main(String[] args) {

		// 先将源文件读取到内存中

//		int eachSize = 1 * 1024*1024;
//		int eachSize = 1 * 512*1024;
//		int eachSize = 1 * 256*1024;
//		int eachSize = 1 * 128*1024;
		int eachSize = 1 * 64*1024;

		File srcFile = new File("D:/workspaces_AllInOne/test-3.0/test-part3.rar");

		// 创建一个文件对象

		splitFile(srcFile, eachSize);

	}

	public static void splitFile(File srcFile, int eachSize) {

		// 判断文件是否符合拆分要求

		if (srcFile.length() == 0) {

			throw new RuntimeException("文件不符合拆分要求");

		}

		byte[] fileContent = new byte[(int) srcFile.length()];

		try {

			// 将文件内容读取到内存中

			FileInputStream fis = new FileInputStream(srcFile);

			fis.read(fileContent);

			fis.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		// 计算要次要拆分为多少份

		int fileNumber;

		if (fileContent.length % eachSize == 0) {

			fileNumber = fileContent.length / eachSize;

		} else {

			fileNumber = fileContent.length / eachSize + 1;

		}

		for (int i = 0; i < fileNumber; i++) {

			String fileName = srcFile.getName() + "-part" + i;

			
			int dirNum = i/20+1; 
			
			String parent = srcFile.getParent()+"/"+ FilenameUtils.getBaseName(srcFile.getName()) +"-tmp/"+ dirNum +"/";
			File fi = new File(parent, fileName);

			File tmp = new File(parent);
			if( !(tmp.exists()) )
			{
				tmp.mkdirs();
			}
			// 在当前文件路径下创建拆分的文件

			byte[] eachContent;

			// 将源文件内容复制到拆分的文件中

			if (i != fileNumber - 1) {

				eachContent = Arrays.copyOfRange(fileContent, eachSize * i, eachSize * (i + 1));

			} else {

				eachContent = Arrays.copyOfRange(fileContent, eachSize * i, fileContent.length);

			}

			try {

				FileOutputStream fos = new FileOutputStream(fi);

				// Base 64 加密
				byte[] miwen = Base64.encodeBase64(eachContent);
				fos.write(miwen);

				// 不加密
//				fos.write(eachContent);
				
				fos.close();

				System.out.printf("输出子文件 %s,其大小是 %d,每个的大小是%d\n", fi.getAbsoluteFile(), fi.length(), eachContent.length);

			}

			catch (Exception e) {

				// TODO: handle exception

				e.printStackTrace();

			}

		}

	}
}
