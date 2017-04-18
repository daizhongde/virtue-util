package person.daizhongde.virtue.util.test;

public class Test {
	   public static void main(String[] args)
	    {
		   String s = "\"datacenter\" : \"99110013\"  ,  // 卓信智恒 zhuoxinzhiheng p_yubin-余斌";
		   s = s.replaceFirst("\"\\s*,\\s*//.*$", "\",");
		   System.out.println("s:"+s);
	    }
}
