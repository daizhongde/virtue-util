package person.daizhongde.virtue.util.dataset;

import java.util.List;
import java.util.Map;

public class DatasetCompare {

	/**
	 * 
	 * @param frontList
	 * @param dbList
	 * @return
	 */
	public String generateBatchSQL(List<Map<String,Object>> frontList,
			List<Map<String,String>> dbList ){
		/* 1、校验字段数量是否一致   
		 * 2、
		 * 
		 * 
		 * */
		
		return "";
	}
	/**
	 * 
	 * @param frontList
	 * @param dbList
	 * @param pk
	 * @return
	 */
	public String generateBatchSQL(List<Map<String,Object>> frontList,
			List<Map<String,String>> dbList, String pk ){
		
		
		return "";
	}
	/**
	 * 
	 * @param frontList
	 * @param dbList
	 * @param pks
	 * @return
	 */
	public String generateBatchSQL(List<Map<String,Object>> frontList,
			List<Map<String,String>> dbList, String[] pks ){
		
		
		return "";
	}
	
	/**
	 * dependence:
	 *  DTO must implements equals method
	 * @param frontList
	 * @param dbList
	 * @return
	 */
	public String generateBatchSQLbyDTO(List<Object> frontList,
			List<Map<String,String>> dbList ){
		
		
		return "";
	}
	/**
	 * 
	 * @param frontList
	 * @param dbList
	 * @param pk
	 * @return
	 */
	public String generateBatchSQLbyDTO(List<Object> frontList,
			List<Map<String,String>> dbList, String pk ){
		
		
		return "";
	}
	/**
	 * 
	 * @param frontList
	 * @param dbList
	 * @param pks
	 * @return
	 */
	public String generateBatchSQLbyDTO(List<Object> frontList,
			List<Map<String,String>> dbList, String[] pks ){
		
		
		return "";
	}
	
}
