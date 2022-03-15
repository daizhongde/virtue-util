package person.daizhongde.virtue.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @Title:  CollectionUtils.java   
 * @Package com.psbc.pfpj.cpabprov.core.util   
 * @Description:     集合转换
 * @author: 湘邮科技-戴忠德  
 * @date:   2021年5月26日 上午10:53:43   
 * @version V1.0 
 * @Copyright: 2021 www.copote.com Inc. All rights reserved. 
 * 注意：本内容仅限于邮储银行湖南省分行内部传阅，禁止外泄以及用于其他的商业目的 
 *
 */
public class CollectionUtils {
	/** 
	 * 
	 * @deprecated  用list2set2代替
	 * 
	 * @param list
	 * @return
	 */
	public Set list2set(List<String> list){
		Set<String> set = new HashSet<String>();
		for(String e : list){
			set.add(e);
		}
		return set;
	}
	
	public Set list2set2(List<String> list){
		return new HashSet<String>(list);
	}


	public Set arr2set(String[] arr){
		Set<String> set = new HashSet<String>();
		for(String e : arr){
			set.add(e);
		}
		return set;
	}
	
	public Set arr2set2(String[] arr){
		List list = Arrays.asList(arr);
		
		return new HashSet<String>(list);
	}
	
	public List set2list(Set set){
		
		return new ArrayList(set);
	}
	
//	public static void main(String args[]){
////		CollectionUtils util = new CollectionUtils();
////		String[] arr =new String[]{"gg","kk","gg"};
////		Set set = util.arr2set2(arr);
////		System.out.println("set:"+JSONObject.toJSONString(set));
//		
//		String ret = "导入文件中共%s条，本次新增%s条，%s条已存在，%s条新增失败。已存在的证件号码%s，失败的证件号码:123,1232,123";
//		int index = ret.indexOf("失败的证件号码:");
//		String seqStr = ret.substring(index+8);
//		String[] invalidSeq = null;
//		if(StringUtils.isNotBlank(seqStr)){
//			invalidSeq = seqStr.split(",");
//		}
//		CollectionUtils util = new CollectionUtils();
//		Set seqSet = util.arr2set2(invalidSeq);
//
//		System.out.println("set:"+JSONObject.toJSONString(seqSet) );
//		
//	}
}
