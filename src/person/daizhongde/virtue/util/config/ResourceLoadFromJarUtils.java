package person.daizhongde.virtue.util.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.IOUtils;

/**
 * @Description TODO
 *
 * @Author daizd
 * @Date 2021/11/20 23:13
 * @Version 1.0
 */
public class ResourceLoadFromJarUtils {

    public static String getCurrentFilePath() {
        String systempath = System.getProperty("java.class.path");
        System.out.println("systempath:"+systempath);

        ProtectionDomain protectionDomain = ResourceLoadFromJarUtils.class.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URI location = null;
        try {
            location = (codeSource == null ? null : codeSource.getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String path = (location == null ? null : location.getSchemeSpecificPart());
        if (path == null) {
            throw new IllegalStateException("Unable to determine code source archive");
        }
        File root = new File(path);
        if (!root.exists()) {
            throw new IllegalStateException(
                    "Unable to determine code source archive from " + root);
        }
        return root.getAbsolutePath();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        /* 资源文件路径,不能以'/'字符字符开头 */
//        String resourcePath = "struts-plugin.xml";
        String resourcePath = "template/Story需求文档202xXxXx-dx.docx";
        /* 获取ClassPath下的所有jar路径 */
//        String[] cps = System.getProperty("java.class.path").split(";");
        String[] cps = new String[]{"D:\\daizd\\Desktop\\temp\\StoryDoc2Excel\\story-converter-1.0-SNAPSHOT.jar"};

        System.out.println("#############  loadResourceFromJarFile 1   #######");
        /* 读取本地Jar文件 */
        for (String cp : cps) {
            if (!cp.endsWith(".jar")) {
                continue;
            }
            InputStream in = loadResourceFromJarFile(cp, resourcePath);
            if (in != null) {
                System.err.println(IOUtils.toString(in));
                in.close();
            }
        }

//        System.out.println("#############  loadResourceFromJarURL 1   #######");
//        /* 读取本地Jar文件 */
//        for (String cp : cps) {
//            if (!cp.endsWith(".jar")) {
//                continue;
//            }
//            InputStream in = loadResourceFromJarURL(cp, resourcePath);
//            if (in != null) {
//                System.err.println(IOUtils.toString(in));
//                in.close();
//            }
//        }

//        System.out.println("#############  loadResourceFromJarURL 2   #######");
//        /* 读取网络Jar文件 */
//        InputStream in = loadResourceFromJarURL(
//                "http://localhost:8080/SpringStruts2Integration/struts2-spring-plugin-2.3.4.1.jar", resourcePath);
//        if (in != null) {
//            System.err.println(IOUtils.toString(in));
//            in.close();
//        }
    }

    /**
     * 从classpath中指定jar中取对应的资源文件
     *
     * @param jarName
     * @param resPath
     * @throws IOException
     */
    public static InputStream loadResourceFromJar(String jarName, String resPath) throws IOException {
        /* 资源文件路径,不能以'/'字符字符开头 */
//        String resourcePath = "template/Story需求文档202xXxXx-dx.docx";
        /* 获取ClassPath下的所有jar路径 */
        String[] cps = System.getProperty("java.class.path").split(";");
//        String[] cps = new String[]{"D:\\daizd\\Desktop\\temp\\StoryDoc2Excel\\story-converter-1.0-SNAPSHOT.jar"};

        System.out.println("#############  loadResourceFromJar(String jarName, String resPath)     #######");
        /* 读取本地Jar文件 */
        for (String cp : cps) {
            if (!cp.endsWith(jarName)) {
                continue;
            }
            InputStream in = loadResourceFromJarFile(cp, resPath);
            if (in != null) {
                return in;
            }
        }
        return null;
    }


    /**
     * 从classpath中所有jar中取对应的资源文件
     *
     * @param resPath
     * @throws IOException
     */
    public static InputStream loadResourceFromJars( String resPath) throws IOException {

        /* 资源文件路径,不能以'/'字符字符开头 */
//        String resourcePath = "template/Story需求文档202xXxXx-dx.docx";
        /* 获取ClassPath下的所有jar路径 */
        String[] cps = System.getProperty("java.class.path").split(";");
//        String[] cps = new String[]{"D:\\daizd\\Desktop\\temp\\StoryDoc2Excel\\story-converter-1.0-SNAPSHOT.jar"};

        System.out.println("#############  loadResourceFromJars( String resPath)      #######");
        /* 读取本地Jar文件 */
        for (String cp : cps) {
            System.out.println("#############  loadResourceFromJars( String resPath)     cp:"+cp);
            if (!cp.endsWith(".jar")) {
                continue;
            }
            InputStream in = loadResourceFromJarFile(cp, resPath);
            if (in != null) {
                return in;
            }
        }
        return null;
    }

    /**
     * 读取Jar文件中的资源
     *
     * @param jarPath
     *   本地jar文件路径
     * @param resPath
     *   资源文件所在jar中的路径(不能以'/'字符开头)
     * @return 如果资源加载失败,返回null
     */
    public static InputStream loadResourceFromJarFile(String jarPath, String resPath) {
        if (!jarPath.endsWith(".jar")) {
            return null;
        }
        try {
            JarFile jarFile = new JarFile(jarPath);
            JarEntry jarEntry = jarFile.getJarEntry(resPath);
            if (jarEntry == null) {
                return null;
            }

            return jarFile.getInputStream(jarEntry);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取Jar文件中的资源
     *
     * @param jarUrl
     *   本地jar文件或网络上(ttp://host:port/subpath/jarfile.jar)jar文件路径
     * @param resPath
     *   资源文件所在jar中的路径(不能以'/'字符开头)
     * @return 如果资源加载失败,返回null
     */
    public static InputStream loadResourceFromJarURL(String jarUrl, String resPath) {
        if (!jarUrl.endsWith(".jar")) {
            return null;
        }
        URL url = null;
        if (jarUrl.startsWith("http://")) {
            try {
                url = new URL("jar:" + jarUrl + "!/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                url = new URL("jar:file:/" + jarUrl + "!/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }
        try {
            JarURLConnection jarURLConnection;
            jarURLConnection = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            JarEntry jarEntry = jarFile.getJarEntry(resPath);
            if (jarEntry == null) {
                return null;
            }
            return jarFile.getInputStream(jarEntry);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}