package person.daizhongde.virtue.util.log4j;

//import org.apache.log4j.Logge;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class BaseLog {
//	public Logge log = Logge.getLogge(BaseLog.class);//老的1.2的log4j

	/** 取得日志记录器Logger   log4j2 */
	public Logger log = LoggerFactory.getLogger(BaseLog.class);
}
