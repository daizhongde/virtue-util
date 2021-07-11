package person.daizhongde.virtue.util.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.alibaba.fastjson.JSON;

public class Vector2Str {

	public static void main(String args[]){
		arr2Str();
		vector2Str();
		list2Str();
		list2Str2();
	}

	public static void arr2Str( ){
		Long[] p1 = new Long[2];
		p1[0] = 234234l;
		p1[1] = 111111l;
		
		System.out.println(JSON.toJSONString(p1));
	}

	public static void vector2Str( ){
		Vector<Long> p1 = new Vector<Long>();
		p1.add(234234l);
		p1.add(111111l);
		
		System.out.println(p1.toString());
	}
	
	public static void list2Str( ){
		List<Long> p1 = new ArrayList<Long>();
		p1.add(234234l);
		p1.add(111111l);
		
		System.out.println(p1.toString());
		String ret = JSON.toJSONString( p1 );
		String inStr = ret.replaceAll("[\\[\\]\"]", "");
		System.out.println("after replace:"+ inStr);
	}
	public static void list2Str2( ){
		List<UinPid> UinPidList = new ArrayList<UinPid>();
		for(int i=0; i<2; i++){

			UinPid e= new UinPid();
			e.setPid(i);
			e.setUin(i);
			UinPidList.add(e);
		}
		
		System.out.println(JSON.toJSONString( UinPidList.toString() ));
		String ret = JSON.toJSONString( UinPidList.toString() );
		String inStr = ret.replaceAll("[\\[\\]\"]", "");
		System.out.println("after replace:"+ inStr);
	}
	

}


class UinPid {
	private long uin;
	private long pid;
	public long getUin() {
		return uin;
	}
	public void setUin(long uin) {
		this.uin = uin;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (pid ^ (pid >>> 32));
		result = prime * result + (int) (uin ^ (uin >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UinPid other = (UinPid) obj;
		if (pid != other.pid)
			return false;
		if (uin != other.uin)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "(" + uin +","+ pid + ")";
	}

}
