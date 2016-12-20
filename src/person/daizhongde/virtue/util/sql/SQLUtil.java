package person.daizhongde.virtue.util.sql;

/**
 * Use for Universal search. Usually, appear to desktop app like notax payment voucher
 * @author daizd
 *
 */
public class SQLUtil {
	
	private ManySwicth ms = new ManySwicth();
	
	/**
	 * 两个条件,七个参数 -组装SQL(分页前)-不带select
	 * @param colName1 
	 * @param a         the first operator index
	 * @param value1
	 * @param conditions
	 * @param colName2
	 * @param b         the second operator index
	 * @param value2
	 * @return
	 */
	public String getWhereConditions(
			String colName1, int columnType1, int a, String value1,
			String conditions, 
			String colName2, int columnType2, int b, String value2) {
//		System.out.println("colName1:"+colName1+" Comparison 1:"+a+" value1:"+value1+" value2:"+value2+" Comparison 2:"+b+" colName2:"+colName2);
		
		String operator1 = ms.getOperator(a);
		String operator2 = ms.getOperator(b);
		
		value1 = ms.getSQLValue(columnType1,value1);
		value2 = ms.getSQLValue(columnType2,value2); 
		
		if( !colName1.trim().equalsIgnoreCase("") && a != 0 && !value1.trim().equalsIgnoreCase("") && 
			!colName2.trim().equalsIgnoreCase("") && b != 0 && !value2.trim().equalsIgnoreCase("")){
			return "where " + colName1+operator1+value1+" "+conditions+" "+colName2+operator2+value2;
		}else if(!colName1.trim().equalsIgnoreCase("") && a != 0 && !value1.trim().equalsIgnoreCase("") && 
				 (colName2.trim().equalsIgnoreCase("") || b == 0 || value2.trim().equalsIgnoreCase(""))){
			return "where " + colName1+operator1+value1;
		}else if((colName1.trim().equalsIgnoreCase("") || a == 0 || value1.trim().equalsIgnoreCase("")) && 
				  !colName1.trim().equalsIgnoreCase("") && b != 0 && !value1.trim().equalsIgnoreCase("")){
			return "where " + colName2+operator2+value2;
		}else{
			return "";
		}
	}
}
