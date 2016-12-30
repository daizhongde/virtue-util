package person.daizhongde.virtue.util.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dao extends BaseDao {

	private static Logger log = LogManager.getLogger( BaseDao.class.getName());
	
	private static Dao dao;

	static {
		dao = new Dao();
	}

	public static Dao getInstance() {
		return dao;
	}

}
