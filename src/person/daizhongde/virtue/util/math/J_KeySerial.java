package person.daizhongde.virtue.util.math;

import java.text.MessageFormat;

/**
 * key序列，可防重启重复
 * @key组成方式yyyymmdd+序号，序号为定长六位，采用右对齐，左补0格方式。
 * @author dzd
 *
 *
 */
public class J_KeySerial {
//	private static int nextval = 0;
	private static int currval = 0;
	/**
	 * 取得下一个六位序号
	 * @return
	 */
	public String nextval(){
		//MessageFormat.format("{0,number,00000000}"
		//String paySerial = "";
		if(currval == 0){//invoke first time
			currval = (int)(Math.random()*100000);//防止程序一天启动多次重启，产生相同的交易流水号
		}
		currval += 1;
		currval = (currval >= 999999)? 1: currval;
//		currval = nextval;
//		Object[] o = new Object[1];
//		o[0] = new Integer(nextval);
		return MessageFormat.format("{0,number,000000}", new Integer(currval));
	}
	/**
	 * 取得当前(最后一次使用的)六位序号,类似于oracle中的sequence
	 * @return
	 */
	public static String currval(){
//		Object[] o = new Object[1];
//		o[0] = new Integer(currval);
		return MessageFormat.format("{0,number,000000}",new Integer(currval));
	}
}
