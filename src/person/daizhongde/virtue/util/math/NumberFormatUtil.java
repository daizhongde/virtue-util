package person.daizhongde.virtue.util.math;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatUtil {

	/**
	 * 本地化格式
	 * <p>
	 * 如果你在美国，运行程序后输出： 
    1,234.56 
    1.234,56 
	 */
	public static void localeFormat(){
        // 得到本地的缺省格式    
        NumberFormat nf1 = NumberFormat.getInstance();    
        System.out.println(nf1.format(1234.56));    
        // 得到德国的格式    
        NumberFormat nf2 =    
            NumberFormat.getInstance(Locale.GERMAN);    
        System.out.println(nf2.format(1234.56));    
	}
	/**
	 * 指定小数位数
	 * <p>
	 * 程序的输出： 
    1234.560 
    1234,560 
	 */
	public static void FormatDecimalCountSpecified (){
        // 得到本地的缺省格式    
        DecimalFormat df1 = new DecimalFormat("####.000");   
        System.out.println(df1.format(1234.56));   
        // 得到德国的格式    
        Locale.setDefault(Locale.GERMAN);   
        DecimalFormat df2 = new DecimalFormat("####.000");   
        System.out.println(df2.format(1234.56));    
	}
	
	/**
	 * 指数形式输出
	 * <p>
	 * 输出： 
    1.235E0003 
	 */
	public static void FormatExponent (){
		DecimalFormat df = new DecimalFormat("0.000E0000");   
        System.out.println(df.format(1234.56));     
	}
	
	/**
	 * 百分数
	 * <p>
	 * 输出： 
    47% 
	 */
	public static void FormatPercent (){
		NumberFormat nf = NumberFormat.getPercentInstance();    
        System.out.println(nf.format(0.47));      
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double pi = 3.1415927;//pi   
        // 取一位整数   
        System.out.println(new DecimalFormat("0").format(pi));// 3   
        // 取一位整数和两位小数   
        System.out.println(new DecimalFormat("0.00").format(pi));// 3.14   
        // 取两位整数和三位小数，整数不足部分以0填补。   
        System.out.println(new DecimalFormat("00.000").format(pi));// 03.142   
        // 取所有整数部分   
        System.out.println(new DecimalFormat("#").format(pi));// 3   
        // 以百分比方式计数，并取两位小数   
        System.out.println(new DecimalFormat("#.##%").format(pi));// 314.16%   
  
        long c = 299792458;   
        // 显示为科学计数法，并取五位小数   
        System.out.println(new DecimalFormat("#.#####E0").format(c));// 2.99792E8   
        // 显示为两位整数的科学计数法，并取四位小数   
        System.out.println(new DecimalFormat("00.####E0").format(c));// 29.9792E7   
        // 每三位以逗号进行分隔。   
        System.out.println(new DecimalFormat(",###").format(c));// 299,792,458   
        // 将格式嵌入文本   
        System.out.println(new DecimalFormat("光速大小为每秒,###米。").format(c));  

	}
	
	/**
	 * @param args
	 */
	public  void Test() {
//		DecimalFormat df1 = new DecimalFormat("###.00");
		DecimalFormat df1 = new DecimalFormat("###0.00");
        System.out.println(df1.format(234234.234634));
        System.out.println(df1.format(34.234634));
        System.out.println(df1.format(""));

        DecimalFormat df2 = new DecimalFormat("0.00E0000");   
        System.out.println(df2.format(23423.34234234));   

        DecimalFormat df3 = (DecimalFormat)NumberFormat.getInstance(Locale.CHINESE);   
        df3.applyPattern("####.000");   
        System.out.println(df3.format(23423.34234234));   
        df3.applyPattern("00.0000%");   
        System.out.println(df3.format(0.5552445));   

        NumberFormat nf1 = NumberFormat.getInstance();   
        System.out.println(nf1.format(13423423.234234));   

        NumberFormat nf2 = NumberFormat.getPercentInstance();   
        System.out.println(nf2.format(0.55));   

	}

}
