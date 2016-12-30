package person.daizhongde.virtue.util.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class BaseLog2 {
	/** 取得日志记录器Logger */	
	private static Logger log = LogManager.getLogger( BaseLog2.class.getName());
}
