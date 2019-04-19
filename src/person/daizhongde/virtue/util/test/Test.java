package person.daizhongde.virtue.util.test;

public class Test {
	   public static void main(String[] args)
	    {
			String ret =String.format("<script type=\"text/javascript\">"
					+ "window.parent.CKEDITOR.tools.callFunction(%s, %s, %s)"
					+ "</script>", "0","http://oss-cn-beijing-zgyz-d01-a.cpct.com.cn/xydjy-cs/"
							+ "images/2018/41000100/2/sp/"
							+ "41000100-2-181109-163714-139-5__FS59IY_Y6_UV_66S1_13.jpg"
							+ "?Expires=4695695364"
							+ "&OSSAccessKeyId=zot3CczI6CHpsQ39"
							+ "&Signature=eO2LEFDwyPsfAYVHeUlR1YX8S6A%3D","王张","王张");
			System.out.println("ret:"+ret);
	    }
}
