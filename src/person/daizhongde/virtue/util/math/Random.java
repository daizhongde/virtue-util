package person.daizhongde.virtue.util.math;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
	public static int getRandomNum(){
		return ThreadLocalRandom.current().nextInt(1,10);
	}
}
