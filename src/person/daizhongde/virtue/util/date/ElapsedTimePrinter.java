package person.daizhongde.virtue.util.date;

import java.util.Date;

public class ElapsedTimePrinter {

	public static void printElapsedTime(Date beginTime, Date endTime, String ... desc){
//		Timestamp beginTime = new Timestamp( beginDate.getTime() - 2*60*1000 );
//		Timestamp endTime = new Timestamp( endDate.getTime());
		long diff = endTime.getTime() - beginTime.getTime();
		
		long days = diff / (1000 * 60 * 60 * 24);
 		 
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60))/1000;
		long milliseconds  = diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60) - seconds*1000;
//		System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		System.out.println(desc[0] + " use time: "+days+" day "+hours+" hour "+minutes+" minute "+seconds+" seconds "+ milliseconds +" milliseconds ");
		
	}
	public static long printElapsedTime3(Date beginTime, Date endTime, String ... desc){
//		Timestamp beginTime = new Timestamp( beginDate.getTime() - 2*60*1000 );
//		Timestamp endTime = new Timestamp( endDate.getTime());
		long diff = endTime.getTime() - beginTime.getTime();
		
		long days = diff / (1000 * 60 * 60 * 24);
 		 
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		
		long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60))/1000;
		long milliseconds  = diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60) - seconds*1000;
//		System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		System.out.println(desc[0] + " use time: "+days+" day "+hours+" hour "+minutes+" minute "+seconds+" seconds "+ milliseconds +" milliseconds ");
		return minutes;
	}
	public static String printElapsedTime2(Date beginTime, Date endTime, String ... desc){
//		Timestamp beginTime = new Timestamp( beginDate.getTime() - 2*60*1000 );
//		Timestamp endTime = new Timestamp( endDate.getTime());
		long diff = endTime.getTime() - beginTime.getTime();
		
		long days = diff / (1000 * 60 * 60 * 24);
 		 
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60))/1000;
		long milliseconds  = diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60) - seconds*1000;
//		System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		System.out.println(desc[0] + " use time: "+days+" day "+hours+" hour "+minutes+" minute "+seconds+" seconds "+ milliseconds +" milliseconds ");
		return desc[0]+" use time: "+seconds+" seconds ";
	}
	public static void main(String args[]){
		Date begin = new Date();
		Date end = new Date();
		
		ElapsedTimePrinter.printElapsedTime(begin, end);
	}

}