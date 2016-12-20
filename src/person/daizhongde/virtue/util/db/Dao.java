package person.daizhongde.virtue.util.db;

import org.apache.log4j.Logger;

public class Dao extends BaseDao {

	private Logger logger = Logger.getLogger(Dao.class);
	
	private static Dao dao;

	static {
		dao = new Dao();
	}

	public static Dao getInstance() {
		return dao;
	}

}
