package person.daizhongde.virtue.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.stream.Stream;

public class SerializeUtil {

    public static void main(String[] args) throws Exception {
      
    }
    /**
     * 将流 写入文件.
     * 
     * @throws IOException
     */
    public static void write(String path, Stream Stream ) throws IOException {
        File file = new File(path);// 指定要写入的文件
        if (!file.exists()) {// 如果文件不存在则创建
            file.createNewFile();
        }
        // 获取该文件的缓冲输出流
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        
        FileOutputStream outstream = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(outstream);
        
        // 写入信息
        out.writeObject(Stream);
        out.flush();// 清空缓冲区
        out.close();// 关闭输出流
        outstream.close();
    }
    /**
     * 将流 写入文件.
     * 
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static void read( String path, Object object ) throws IOException, ClassNotFoundException {
        File file = new File(path);// 指定要写入的文件
        if (!file.exists()) {// 如果文件不存在则创建
            file.createNewFile();
        }
        // 获取该文件的缓冲输出流
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        
        FileInputStream instream = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(instream);
        
        while(in.available()!=-1){//这里如何判断是否已经读到结尾？
        	object = in.readObject();
        }
        
        // 写入信息
//        in.writeObject(Stream);
//        in.flush();// 清空缓冲区
        in.close();// 关闭输出流
        instream.close();
    }
}
