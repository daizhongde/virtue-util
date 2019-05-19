package person.daizhongde.virtue.util.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class SerializeUtil {

    public static void main(String[] args) throws Exception {
      
    }

    /**
     * 将流 写入文件.
     * 
     * @throws IOException
     */
    public static void write(String fileAbsPath, Object obj ) throws IOException {
        File file1 = new File(fileAbsPath.substring(0, fileAbsPath.lastIndexOf("/")));// 指定要写入的文件
        File file2 = new File(fileAbsPath);
        
        if (!file1.exists()) {// 如果文件不存在则创建
        	file1.mkdir();
        }
        if (file2.exists()) {// 如果文件存在则删除
            file2.delete();
        }
        if (!file2.exists()) {// 如果文件不存在则创建
            file2.createNewFile();
        }
        
        // 获取该文件的缓冲输出流
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        
        FileOutputStream outstream = new FileOutputStream(fileAbsPath);
        ObjectOutputStream out = new ObjectOutputStream(outstream);
        
        // 写入信息
        out.writeObject(obj);
        out.flush();// 清空缓冲区
        out.close();// 关闭输出流
        outstream.close();
    }
    /**
     * 将Map对象 写入文本文件.
     * 
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static void writeMap2Txt( String path, Map<Integer, String> object ) throws IOException, ClassNotFoundException {
        File file = new File(path);// 指定要写入的文件
        if (!file.exists()) {// 如果文件不存在则创建
            file.createNewFile();
        }
        // 获取该文件的缓冲输出流
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        // 写入信息
//        bufferedWriter.write("你好世界");
//        bufferedWriter.newLine();// 表示换行
//        bufferedWriter.write("hello world");
        Iterator<Integer> it = object.keySet().iterator();
//        while( it.hasNext() ){
        for(int i=0, j=object.size(); i<j; i++){
        	Integer q = it.next();
        	bufferedWriter.write(String.valueOf(q)+"	"+object.get(q));
        	if(i != j-1){
            	bufferedWriter.newLine();// 表示换行
        	}
        }
        
        bufferedWriter.flush();// 清空缓冲区
        bufferedWriter.close();// 关闭输出流
        
    }
    
    /**
     * 把文件读到对象中.
     * 
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Map<Integer, String> read( String path, Map<Integer, String> object ) throws IOException, ClassNotFoundException {
        File file = new File(path);// 指定要写入的文件
        if (!file.exists()) {// 如果文件不存在则创建
            file.createNewFile();
        }
        // 获取该文件的缓冲输出流
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        
        FileInputStream instream = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(instream);
        
//        while(in.available()!=-1){//这里如何判断是否已经读到结尾？
//        	object = (ArrayList<TIsoftstoneEmployee>)in.readObject();
//        }

//        while(in.available()!=-1 && in.available()!=-1){
        	object = (Map<Integer, String>)in.readObject();
        	System.out.println(object.size());
//        }
        
        
        // 写入信息
//        in.writeObject(Stream);
//        in.flush();// 清空缓冲区
        in.close();// 关闭输出流
        instream.close();
        return object;
    }

}
