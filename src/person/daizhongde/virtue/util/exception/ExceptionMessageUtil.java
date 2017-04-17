package person.daizhongde.virtue.util.exception;

public class ExceptionMessageUtil {
	public static String searchRootCauseMessage(Exception e ){
		Throwable e2 = e;
		while(e2.getCause() != null ){
			e2 = e2.getCause();
		}
		return e2.getLocalizedMessage();
	}
}
