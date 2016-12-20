package person.daizhongde.virtue.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {
	public static Object invokeMethod(Object obj, String methodName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Class clazz = obj.getClass(); 

        Method m = clazz.getDeclaredMethod( methodName );
        
        return m.invoke(obj); 
	}
	
	
	
    public static void main(String args[]) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Foo foo = new Foo("这个一个Foo对象！");
        Class clazz = foo.getClass(); 
        Method m1 = clazz.getDeclaredMethod("outInfo");
        Method m2 = clazz.getDeclaredMethod("setMsg", String.class);
        Method m3 = clazz.getDeclaredMethod("getMsg");
        m1.invoke(foo); 
        m2.invoke(foo, "重新设置msg信息！"); 
        String msg = (String) m3.invoke(foo); 
        System.out.println(msg); 
    }
}

class Foo { 
    private String msg; 

    public Foo(String msg) { 
        this.msg = msg; 
    } 

    public void setMsg(String msg) {
        this.msg = msg; 
    } 

    public String getMsg() { 
        return msg; 
    } 

    public void outInfo() {
        System.out.println("这是测试Java反射的测试类"); 
    } 
}