package person.daizhongde.virtue.util.file;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

public class FileMerge {

	public static void main(String[] args) {

		// 先将源文件读取到内存中

//		int eachSize = 1 * 1024*1024;
//		int eachSize = 1 * 512*1024;
//		int eachSize = 1 * 256*1024;
		int eachSize = 1 * 128*1024;
//		int eachSize = 1 * 64*1024;

		/**
		 * test-part3-tmp   -->  test-part2-tmp.rar
		 * 
		 */
//		File srcFile = new File("E:/Download/test-3.0/test-part3.rar");
		/**
		 * Janeryfinish-tmp   -->  Janeryfinish-tmp.rar
		 * 
		 */
		File srcFile = new File("E:/Download/Janeryfinish.rar");

		// 创建一个文件对象

		mergeFile(srcFile, eachSize);

	}

	public static void mergeFile(File srcFile, int eachSize) {

//		// 判断文件是否符合拆分要求
//  
//		if (srcFile.length() == 0) {
//
//			throw new RuntimeException("文件不符合拆分要求");
//
//		}

		String parent = srcFile.getParent()+"/"+ FilenameUtils.getBaseName(srcFile.getName()) +"-tmp";

		File pdir = new File(parent);
		if( !(pdir.exists()) )
		{
			throw new RuntimeException("没有需要合并处理的目录！");
		}

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(parent+".rar");
			boolean hasNextDir = true;
			boolean hasNextFile = true;
			int i =1;// 目录名
			int j =0;// 文件序号
			
			
			while (hasNextDir) {
	
				String dirS = parent + "/" + i +"/";
				File dir = new File(dirS);
				if( !(dir.exists()) )
				{
					System.out.println("#########  所有目录都已处理！   ##########");
					break;
				}
				
				while (hasNextFile) {
					String fileName = srcFile.getName() + "-part" + j;
					File fi = new File(dirS, fileName);
					if( !(fi.exists()) )
					{
						System.out.println("######### "+dirS +" 目录下所有文件都已处理！   ##########");
						break;
					}
					
					byte[] fileContent = new byte[(int) fi.length()];

					/* 1、将内容 读到字节数组中  */ 
					try {
						// 将文件内容读取到内存中
						FileInputStream fis = new FileInputStream(fi);
						fis.read(fileContent);
						fis.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					/* 2、将内容写到目标文件  */ 
					

					// Base 64 解密
					byte[] minwen = Base64.decodeBase64(fileContent);
					fos.write(minwen);
					// 不解密
//					fos.write(eachContent);
					fos.flush();
					j++;
				}// end of file circle
				i++;
			}// end of dir circle
		}

		catch (Exception e) {

			// TODO: handle exception

			e.printStackTrace();

		}finally{

			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
