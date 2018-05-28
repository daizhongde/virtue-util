package person.daizhongde.virtue.util.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/**
 * 
 * @Author: daizd   2018-3-1 
 * @modifier  liao  2018-04-11
 */
public class FileUtil {

    /**
     * Java文件操作 获取文件扩展名
     * <p>
     * 可以直接使用common-io工具包，deprecated by liao 2018-04-11
     * <p>
     * @Author: daizd   2018-3-1 
     */
    @Deprecated
    public static String getExtensionName(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     * <p>
     * 可以直接使用common-io工具包，deprecated by liao 2018-04-11
     * <p>
     * @Author: daizd   2018-3-1 
     */
    @Deprecated
    public static String getFileNameNoEx(String filePath) {
        String full = FilenameUtils.getFullPath(filePath);
        String base = FilenameUtils.getBaseName(filePath);
        if (StringUtils.isAnyBlank(full, base)) {
            return null;
        }
        return String.format("%s%s", full, base);
    }


    public static String getFilePath(String file) {
        String absPath = "";
        String classpath = FileUtil.class.getResource("/").getPath();//得到工程名WEB-INF/classes/路径
        try {
            classpath = java.net.URLDecoder.decode(classpath, "UTF-8");
            if (file.startsWith("com/")) {
                //同目录文件夹：classes，m2e-jee，test-classes
                int index = classpath.indexOf("/test-classes/");
                /*
					path:/D:/daizd/Workspaces/MyEclipse%202015%20CI/migration/target/test-classes/
					or: D:/tomcat/webapps/migration/WEB-INF/classes/
					or: D:\AppData\Workspaces\MyEclipse 2015\message\bin\
                */
                int binIndex = classpath.indexOf("/bin/");
                int classesIndex = classpath.indexOf("/classes/");

                if (binIndex != -1) {
                    classpath = classpath.substring(1, index == -1 ? classpath.indexOf("/bin/") : index);
                    //从路径字符串中取出工程路径
                    if (file.indexOf("authority") != -1) {
                        absPath = classpath + "/" + file;
                    } else {
                        absPath = classpath + "/bin/" + file;
                    }
                } else if (classesIndex != -1) {
                    classpath = classpath.substring(1, index == -1 ? classpath.indexOf("/classes/") : index);
                    //从路径字符串中取出工程路径
                    if (file.indexOf("authority") != -1) {
                        absPath = classpath + "/" + file;
                    } else {
                        absPath = classpath + "/classes/" + file;
                    }
                }
            } else if (file.startsWith("/WEB-INF/")) {
                //从路径字符串中取出工程路径
                classpath = classpath.substring(0, classpath.indexOf("/WEB-INF/"));
                absPath = classpath + "/" + file;
            } else if (file.indexOf("/") == -1) {
                absPath = classpath + "/" + file;
            } else {
                absPath = file;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return absPath;
    }


    /**
     * DOC 写入信息.
     * <p>
     * 可以直接使用common-io工具包，deprecated by liao 2018-04-11
     *
     * @throws IOException
     */
    @Deprecated
    public static void write2File(InputStream is, String path) throws IOException {
        FileUtils.writeByteArrayToFile(new File(path), IOUtils.toByteArray(is));
    }

    /**
     * String 写入信息.
     * <p>
     * 可以直接使用common-io工具包，deprecated by liao 2018-04-11
     *
     * @throws IOException
     */
    @Deprecated
    public static void write2File(String data, String path) throws IOException {
        FileUtils.writeStringToFile(new File(path), data);

    }
}
