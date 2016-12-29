package person.daizhongde.virtue.util.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import person.daizhongde.virtue.util.collection.MapUtils;

public class FileNameUtil {
	/**
	 * file rename 
	 * <p>
	 * substring caoqun image tie(blog) title
	 * @param source
	 * @param str
	 * @param orientation front\ back
	 * @throws IOException
	 */
	public void renameFile(File file, String str, String orientation, boolean isStrInRet) throws IOException {
//		java.io.File file = new java.io.File(source);
		String absPath = file.getAbsolutePath();
		String oldFileName = file.getName();
		String fileEx = FileUtil.getExtensionName( oldFileName );
		String parentDirectory = absPath.substring(0,absPath.indexOf(oldFileName));
		
		String oldFileNameNoEx = FileUtil.getFileNameNoEx( oldFileName );
		String newFileNameNoEx = "";
		String newFileName = "";
		
		if("front".trim().toLowerCase().equalsIgnoreCase(orientation))
		{
			if(isStrInRet){
				newFileNameNoEx = oldFileNameNoEx.substring(0, oldFileNameNoEx.indexOf(str)+str.length() );
			}else{
				newFileNameNoEx = oldFileNameNoEx.substring(0, oldFileNameNoEx.indexOf(str));
			}
		}
		else if("back".trim().toLowerCase().equalsIgnoreCase(orientation))
		{
			if(isStrInRet){
				newFileNameNoEx = oldFileNameNoEx.substring( oldFileNameNoEx.indexOf(str) );
			}else{
				newFileNameNoEx = oldFileNameNoEx.substring( oldFileNameNoEx.indexOf(str)+ str.length() );
			}
		}
		else
		{
			newFileNameNoEx = oldFileNameNoEx;
		}
		if(file.isDirectory()){
			newFileName = newFileNameNoEx;
		}else{
			newFileName = newFileNameNoEx + "."+fileEx;
		}
		System.out.println( parentDirectory + newFileName);
		if(!file.exists()){
            return;//重命名文件不存在
        }
		File newfile =new File( parentDirectory + newFileName);
		if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
            System.out.println(newFileName+"已经存在！"); 
        else{
    		file.renameTo( newfile );
        }
	}
	/**
	 * 重命名指定目录下所有文件
	 * @param file
	 * @param str
	 * @param orientation
	 * @param isStrInRet
	 * @throws IOException
	 */
	public void renameFiles(File file, String str, String orientation, boolean isStrInRet) throws IOException {
		FileNameUtil util = new FileNameUtil();
		File[] tempList = file.listFiles();
		System.out.println("该目录下对象个数：" + tempList.length);
		for (int i = 0; i < tempList.length; i++) {
//			if (tempList[i].isFile()) {
//				System.out.println("文     件：" + tempList[i]);
//			}
//			if (tempList[i].isDirectory()) {
//				System.out.println("文件夹：" + tempList[i]);
//			}
			util.renameFile( tempList[i], "P]", "front", true );
		}
	}
	/**
	 * validate file name 
	 * <p>for caoqun title
	 * @param fileName
	 * @return
	 */
	public static boolean isValidFileName(String fileName) {
		if (fileName == null || fileName.length() > 255)
			return false;
		else
			return fileName
					.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
	}

	/**
	 * file rename 
	 * <p>
	 * substring caoqun image tie(blog) title
	 * @param source
	 * @param str
	 * @param orientation front\ back
	 * @throws IOException
	 */
	public void renameFileByConvert(File file, Map<String,String> map ) throws IOException {
//		java.io.File file = new java.io.File(source);
		String absPath = file.getAbsolutePath();
		String oldFileName = file.getName();
		String fileEx = FileUtil.getExtensionName( oldFileName );
		String parentDirectory = absPath.substring(0,absPath.indexOf(oldFileName));
		
		String oldFileNameNoEx = FileUtil.getFileNameNoEx( oldFileName );
		String newFileNameNoEx = "";
		String newFileName = "";
		
		newFileNameNoEx = map.get(oldFileNameNoEx);
		
		if(file.isDirectory()){
			newFileName = newFileNameNoEx;
		}else{
			newFileName = newFileNameNoEx + "."+fileEx;
		}
		System.out.println( parentDirectory + newFileName);
		if(!file.exists()){
            return;//重命名文件不存在
        }
		File newfile =new File( parentDirectory + newFileName);
		if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
            System.out.println(newFileName+"已经存在！"); 
        else{
    		file.renameTo( newfile );
        }
	}
	
	public String calcDirectoryByParameter(int q, int range){
		String subDirectory = "";
		int begin, end;
		int n = (q-1)%range;
		begin = q-n;
		end = begin + range-1;
//		System.out.println("n:"+n+",begin:"+begin+",end:"+end );
		subDirectory = String.valueOf(begin)+"-"+String.valueOf(end);
		return subDirectory;
	}
	/**
	 *  D:/usr/http/[自创]第一贴 夫妻间的真实诱惑  [16P]113
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		FileNameUtil util = new FileNameUtil();
		Map map1  = new HashMap();
		// D:/usr/http/dat/map_q_name_20161228_140000.dat
		map1 = SerializeUtil.read("D:/usr/http/dat/map_q_name_20161228_140000.dat", map1);
		System.out.println("map1.size() :"+ map1.size() );//6963
		Map map2 = MapUtils.reverse(map1);
		System.out.println("map2.size() :"+ map2.size() );//6858
		if(map1.size()!=map2.size()){
			return;
		}
		util.renameFileByConvert(new File("D:/usr/http/caoqun"), map2 );
		
	}
}
