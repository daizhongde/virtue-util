package person.daizhongde.virtue.util.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Naocs
 * <p>
 * 多网卡ip获取工具类
 * <li>使用局域网ip:
 * inetutils.use-only-site-local-interfaces=true</li>
 * <li>支持网卡数组，可以忽略多个网卡:
 * inetutils.ignored-interfaces=eth0,eth1</li>
 * <li>支持优先选择匹配的ip，支持正则匹配和前缀匹配:
 * inetutils.preferred-networks=30.5.124.(25[0-5]|2[0-4]\\d|((1d{2})|([1-9]?\\d))),30.5.124.(25[0-5]|2[0-4]\\d|((1d{2})|([1-9]?\\d)))</li>
 */
public class INetUtils {


	    /**
	     * 获取主机名称
	     * 
	     * @return
	     * @throws UnknownHostException
	     */
	    public static String getHostName() throws UnknownHostException {
	        return InetAddress.getLocalHost().getHostName();
	    }

	    /**
	     * 获取系统首选IP
	     * 
	     * @return
	     * @throws UnknownHostException
	     */
	    public static String getLocalIP() throws UnknownHostException {
	        return InetAddress.getLocalHost().getHostAddress();
	    }

	    /**
	     * 获取所有网卡IP，排除回文地址、虚拟地址
	     * 
	     * @return
	     * @throws SocketException
	     */
	    public static String[] getLocalIPs() throws SocketException {
	        List<String> list = new ArrayList<String>();
	        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
	        while (enumeration.hasMoreElements()) {
	            NetworkInterface intf = enumeration.nextElement();
	            if (intf.isLoopback() || intf.isVirtual()) { //
	                continue;
	            }
	            Enumeration<InetAddress> inets = intf.getInetAddresses();
	            while (inets.hasMoreElements()) {
	                InetAddress addr = inets.nextElement();
	                if (addr.isLoopbackAddress() || !addr.isSiteLocalAddress() || addr.isAnyLocalAddress()) {
	                    continue;
	                }
	                list.add(addr.getHostAddress());
	            }
	        }
	        return list.toArray(new String[0]);
	    }

	    /**
	     * 判断操作系统是否是Windows
	     * 
	     * @return
	     */
	    public static boolean isWindowsOS() {
	        boolean isWindowsOS = false;
	        String osName = System.getProperty("os.name");
	        if (osName.toLowerCase().indexOf("windows") > -1) {
	            isWindowsOS = true;
	        }
	        return isWindowsOS;
	    }

	    public static void main(String[] args) {
	        try {
	            System.out.println("主机是否为Windows系统：" + INetUtils.isWindowsOS());
	            System.out.println("主机名称：" + INetUtils.getHostName());
	            System.out.println("系统首选IP：" + INetUtils.getLocalIP());
	            System.out.println("系统所有IP：" + ","+INetUtils.getLocalIPs(  ));
	        } catch (UnknownHostException e) {
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}