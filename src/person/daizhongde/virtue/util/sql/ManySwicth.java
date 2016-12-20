package person.daizhongde.virtue.util.sql;

/**
 * 
 * oracle11gR2: 
 * 12   VARCHAR2 
 * -9   NVARCHAR2
 * 1    CHAR
 * 
 * 2    NUMBER
 * 100  BINARY_FLOAT
 * 101  BINARY_DOUBLE
 * 
 * 93   DATE  TIMESTAMP
 * -101 TIMESTAMP WITH TIME ZONE
 * -102 TIMESTAMP WITH LOCAL TIME ZONE
 * 
 * SQLServer2008R2
 * 12   varchar 
 * -9   nvarchar
 * 1    char
 * -15  nchar
 * 
 * 2    numeric
 * 3    money  smallmoney
 * 4    int
 * 5    smallint
 * 8    float
 * -5   bigint
 * 
 * 91   date 
 * 92   time
 * 93   datetime datetime2
 * -2   timestamp
 * 
 * MySQL 5.1
 * 1  char
 * 12 varchar
 * -1 varchar
 * 
 * 4  int unsigned,mediumint,int
 * 7  float,float undigned
 * 8  double
 * -6 tinyint unsigned
 * 5  smallint
 * -5 bigint
 * 3  deciaml

 * 91 date,year
 * 92 time
 * 93 timestamp,datetime
 * <p>
 * Use for convert to insert/update/delete sql when parse xml.
 * No parameter, it is use for commonly sql convert
 * @author dzd
 *
 */
public class ManySwicth {
	/**
	 * 通过比较运算符下拉框的索引返回SQL运算符,recently deal wiht first six conditions
	 * @param index
	 * @return
	 */
	public String getOperator(int index){
//		String operator = "";
		switch(index){
		case 0: 
//			System.out.println("0"); 
			return "";
		case 1: return "="; 
		case 2: return "!="; 
		case 3: return ">"; 
		case 4: return ">="; 
		case 5: return "<"; 
		case 6: return "<="; 
		case 7: return "="; 
		case 8: return "="; 
		case 9: return "="; 
		case 10: return "="; 
		case 11: return "="; 
		case 12: return "="; 
		default : System.out.println("default");  return "";
		}
//		return operator;
	}
	/**
	 * @param type SQLType
	 * @param value
	 * @return
	 */
	public String getSQLValue(int type,String value){
		if(value.trim().equalsIgnoreCase("") || value == null){
			return value;
		}
		switch(type){
		case 12: 
		case 1: 
		case -9: 
		case -15: 
		case -1: return "'"+value+"'"; 
		case 2: 
		case 3: 
		case 4: 
		case 5: 
		case 7: 
		case 8: 
		case -5:
		case -6:
		case 100:  	
		case 101:return value;
		case 93: return "to_date('" + value + "','yyyymmdd')"; 
		default : System.out.println("没有对应的列类型！");  return "";
		}
	}

}