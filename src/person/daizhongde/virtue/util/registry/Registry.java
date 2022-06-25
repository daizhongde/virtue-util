package person.daizhongde.virtue.util.registry;

import java.util.prefs.*;

/**
 * 
 * @Description:
 * 
 * 
 *    此类只对win7好使
 * 
 * 	最后再说明几点：
 * 
 * 你的节点的首字母不要大写,不然在注册表中的项前就加了一个“/”;
 *  注册表中的值也可以导入到一个XML文件中,具体方法见有关文档. 
 *  如果把代码中的
 *    Preferences pre =  Preferences.systemRoot().node("/javaplayer"); 
 *  换成Preferences pre = Preferences.userRoot().node("/javaplayer"); 
 *  则相应的HKEY_LOCAL_MACHINE就成为HKEY_LOCAL_USER。
 * 
 * @author daizd
 * @date 2021年11月19日
 *
 */
public class Registry {
	String[] keys = { "version", "initial", "creator" };
	String[] values = { "1.3", "ini.mp3", "caokai1818@sina.com" };

	// 把相应的值储存到变量中去
	public void writeValue() {
		// HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs 下写入注册表值.
		Preferences pre = Preferences.systemRoot().node("/javaplayer");
		for (int i = 0; i < keys.length; i++) {
			pre.put(keys[i], values[i]);
		}
	}

	public static void main(String[] args) {
		Registry reg = new Registry();
		reg.writeValue();
	}
}