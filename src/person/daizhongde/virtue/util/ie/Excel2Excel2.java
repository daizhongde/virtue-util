package person.daizhongde.virtue.util.ie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import person.daizhongde.virtue.util.exception.BusinessException;
import person.daizhongde.virtue.util.ie.POICellStyle;
import person.daizhongde.virtue.util.ie.POICellUtil;

/**
 * 这版是针对无数据源、有数据对象的版本
 * support multi-thread
 * @author daizd 20210704 
 *
 */

class MyCellValue2 {
	String sheet;
	int row;
	int col;
	boolean isError;// 如果 sql 执行出了异常可设置为　false
	Object[][] values;
	int[][] columnTypes, scales;
	
	@Override
	public String toString() {
		return "MyCellValue2 [sheet=" + sheet + ", row=" + row + ", col=" + col + ", isError=" + isError + ", values="
				+ Arrays.toString(values) + ", columnTypes=" + Arrays.toString(columnTypes) + ", scales="
				+ Arrays.toString(scales) + "]";
	}
	
}

/**
 * 
 *  核心步骤：
 *    1、循环读sheet row cell 获取变量并从入参map中获取值存入自定义cell,然后将cell放入  ArrayList<MyCellValue2> cellList
 *         并把结果存入values、列属性存入columnTypes, scales
 *         i、判断cell是否变量，如果是进入下一步，否则进入下一个循环
 *         i、先获取变量名 ，得到key
 *         i、通过key获取值values、columnTypes、scales
 *         
 *    2、遍历自定义cell对象，将其value按列属性创建Excel单元格格式并赋值填充到指定位置
 *    
 * @author daizd
 * @date 2021年7月10日
 */
public class Excel2Excel2 {
	
	
	private static final Logger log = LoggerFactory.getLogger(Excel2Excel2.class);
	
	private ArrayList<MyCellValue2> cellList = new ArrayList<MyCellValue2>();

	private POICellStyle cs; // 不使用此类为了保留模板中的样式
    
	
	/** input file's path is absolute path
	 * @throws Exception */
	public void exchangeExcel( String inputFile, String outputFile, String fileContentType, 
			Map<String, Object> map ) throws Exception{
		if(!new File(inputFile).exists() ){
			throw new BusinessException("input file is not exist!");
		}
		try{
			this.exchangeExcel(new File(inputFile), outputFile, fileContentType,
					map);
		}catch(Exception e){
			throw e;
		}finally{
//			con_Default.close();
//			closeAllConnections();
		}
	}
	public void exchangeExcel( File inputFile, String outputFile, String fileContentType, 
			Map<String, Object> map ) throws Exception{

		//创建工作文档对象  
        Workbook wb = null;
        
        if (fileContentType.equals("application/vnd.ms-excel")) {
           wb = new HSSFWorkbook(new FileInputStream(inputFile));  
	    }
	    else if(fileContentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))  
	    {
	       wb = new XSSFWorkbook(new FileInputStream(inputFile));  
	    }  
        else  
        {  
            System.out.println("The file with the wrong format！");  
        }
        
        cs = new POICellStyle(wb);
        
        /* 
         * 
		 *         i、判断cell是否变量，如果是进入下一步，否则进入下一个循环
		 *         i、先获取变量名 ，得到key
		 *         i、通过key获取值values、columnTypes、scales
		 *         
         */
        for(int sn=0; sn<wb.getNumberOfSheets(); sn++) {
//        	HSSFSheet t = new HSSFSheet();
        	Sheet sheet = wb.getSheetAt(sn);
            System.out.println("Sheet #" + sn + " : " + sheet.getSheetName());
            
            for(Row row : sheet) {
//               System.out.println(sheet.getSheetName() + "  Row " + row.getRowNum());
               
               for(Cell cell : row) {
            	  
            	  if(cell.getCellType() != HSSFCell.CELL_TYPE_STRING){
            		  continue;
            	  }
                  String value = cell.getStringCellValue();
                  value = value.trim().replaceAll("  ", " ");
                  
                  if(this.isVar(value)){
                	  String name = this.getVarName(value);
                	  Object obj = map.get(name);

        			  MyCellValue2 cv = getUserDefineCell(sheet, cell, obj);
                      
        			  cellList.add(cv);
        			  
//                		  Date endTime = new Date();
//                		  long diff = endTime.getTime() - beginTime.getTime();//这样得到的差值是微秒级别
//                		  long days = diff / (1000 * 60 * 60 * 24);
                		 
//                		  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//                		  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//                		  long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60))/1000;
//                		  System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
//                		  System.out.println(sn+" "+row.getRowNum()+" "+cell.getColumnIndex()+" use time: "+days+" day "+hours+" hour "+minutes+" minute "+seconds+" seconds");
					
                  }
               }
            }
         }// end of for
		
		System.out.println("############  cellList.size(): " + cellList.size() + " ############");
		System.out.println("############  cellList: " + cellList.toString() );
		for (MyCellValue2 cv : cellList) {
			Sheet ws = wb.getSheet(cv.sheet);

			for (int row = cv.row; row < cv.row + cv.values.length; row++) {// 行
				System.out.println("###### 第<" + row + ">行  #######");
				for (int col = cv.col; col < cv.col + cv.values[0].length; col++) {// 列
					System.out.println("###### 第<" + row + ">行 ， 第<" + col + ">列  #######");

//					 cs.initializeRequiredStyle(
//					 cv.columnTypes[row - cv.row][col - cv.col],
//					 cv.scales[row - cv.row][col - cv.col]);
					 
					System.out.println("#####  cv.values: " + JSONObject.toJSONString(cv.values));
					this.setContent(ws, row, col, cv.values[row - cv.row][col - cv.col],
							cv.columnTypes[row - cv.row][col - cv.col], cv.scales[row - cv.row][col - cv.col]);
				}
			}

		}// end of for
		
		// end deleted sheet
		FileOutputStream out = new FileOutputStream(outputFile);
		wb.write(out);
		out.close();
		wb.close();
	}//end of method 
	
//	public void setContent(Sheet sht, int row, int col, BigDecimal value) {
////		System.out.println("value:"+value+",row:"+row+",col:"+col);
//		Cell cell = sht.getRow(row).getCell(col);
//		cell.setCellValue( null==value? -1 : value.doubleValue() );
//	}
	public void setContent(Sheet sht, int row, int col, Object value, int columnType, int scale ) {
		Row row0 = sht.getRow(row);
		if(null == row0){
			row0 = sht.createRow(row);
		}
		Cell cell = row0.getCell(col);
		if(null == cell){
			cell = row0.createCell(col);
		}
		POICellUtil.setCellValueT(value, cell, columnType );
//		if(columnType!=91){
//			POICellUtil.setCellValueT(value, cell, columnType );
//		}else{			
//			cs.initializeRequiredStyle(columnType, scale);
//			POICellUtil.setCellValueST(value, cell,cs, columnType, scale );
//		}
//		cell.setCellValue( String.valueOf( value ) );
	}
	
	
	public String getVarName(String value){
		return value.replaceAll("[$|{|}| ]", "");
	}
	
	public int getColumnType( Object obj) {
  	    Class type = obj.getClass();
  	    if ("String".equalsIgnoreCase(type.getSimpleName())) {
			return 1;
		} else if ("Integer".equalsIgnoreCase(type.getSimpleName())
				|| "Long".equalsIgnoreCase(type.getSimpleName()) 
				|| "BigDecimal".equalsIgnoreCase(type.getSimpleName()) 
				|| "Double".equalsIgnoreCase(type.getSimpleName()) 
				|| "Float".equalsIgnoreCase(type.getSimpleName()) 
		) {
			return 3;
		} else if ("Date".equalsIgnoreCase(type.getSimpleName())) {
			return 91;
		}else{
			return -1; //当字符串处理
		}
	}
	
	/** 
	 * 
	 * @param obj 仅支持BigDecimal 类型
	 * @return
	 */
	public int getScales( Object obj) {
  	    Class type = obj.getClass();
  	    if (!"BigDecimal".equalsIgnoreCase(type.getSimpleName())) {
			return 0;
//  	    	throw new RuntimeException("程序bug，需及时修复！");
		} 
  	    BigDecimal deci = (BigDecimal)obj;
  	    return deci.scale();
	}
	
	
	/** 
	 * 
	 * @param sheet
	 * @param cell
	 * @param obj  要求传入前不为null
	 * @return
	 * @throws Exception
	 */
	public MyCellValue2 getUserDefineCell(Sheet sheet, Cell cell, Object obj) throws Exception {		
		System.out.println("init userdefined cell ...");
		System.out.println("#####  obj:" + JSONObject.toJSONString(obj )  );

		MyCellValue2 cv = new MyCellValue2();
		cv.sheet = sheet.getSheetName();
	    cv.row = cell.getRowIndex();
		cv.col = cell.getColumnIndex();
		
		if(null==obj){ cv.isError=true; return cv; }
		
  	    Class type = obj.getClass();
  	    
  	    System.out.println("type.getName():"+type.getName());
  	    System.out.println("type.getSimpleName():"+type.getSimpleName());
  	    System.out.println("type.getTypeName():"+type.getTypeName());
  	    System.out.println("type.isAssignableFrom(String.class) :"+type.isAssignableFrom(String.class) );
  	    System.out.println("type.isAssignableFrom(Integer.class) :"+type.isAssignableFrom(Integer.class) );
  	    System.out.println("type.isAssignableFrom(Date.class) :"+type.isAssignableFrom(Date.class) );
  	    System.out.println("type.isAssignableFrom(List.class) :"+type.isAssignableFrom(List.class) );
  	    System.out.println("type.isInstance(\"String\"):"+type.isInstance( "" ));
  	    System.out.println("type.isInstance(\"Integer\"):"+type.isInstance( new Integer(1)));
  	    System.out.println("type.isInstance(\"Date\"):"+type.isInstance( new Date() ));
  	    System.out.println("type.isInstance(\"List\"):"+type.isInstance( new ArrayList() ));

  	    System.out.println("o instanceof java.util.List:"+ (obj instanceof java.util.List ) );
  	    System.out.println("type.isArray():"+ (type.isArray() ) );

		  
//		  if( !(o instanceof java.util.List) && !(type.isArray() )
	  /* String  Integer   Date  ArrayList  Integer[]  Integer[][]     String[]  String[][]      Object[]   Object[][]
 	   * 1-VARCHAR 3-NUMERIC  91-DATE
	   * 
	   */
		if ("String".equalsIgnoreCase(type.getSimpleName())) {
			cv.values = new Object[][] { { obj } };
			int[][] tmp = { { 1 } };
			cv.columnTypes = tmp;
			
			int[][] tmpScale = { { 0 } };
			cv.scales = tmpScale;
		} else if ("Integer".equalsIgnoreCase(type.getSimpleName())) {
			cv.values = new Object[][] { { obj } };
			int[][] tmp = { { 3 } };
			cv.columnTypes = tmp;
			
			int[][] tmpScale = { { 0 } };
			cv.scales = tmpScale;
		} else if ("Date".equalsIgnoreCase(type.getSimpleName())) {
			cv.values = new Object[][] { { obj } };
			int[][] tmp = { { 91 } };
			cv.columnTypes = tmp;

			int[][] tmpScale = { { 0 } };
			cv.scales = tmpScale;
		} else if ("BigDecimal".equalsIgnoreCase(type.getSimpleName())
				|| "Double".equalsIgnoreCase(type.getSimpleName())
				|| "Float".equalsIgnoreCase(type.getSimpleName())) {
			cv.values = new Object[][] { { obj } };
			int[][] tmp = { { 3 } };
			cv.columnTypes = tmp;

			int[][] tmpScale = { { 2 } };// 带小数的默认2位小数
			cv.scales = tmpScale;
		}  else if ("ArrayList".equalsIgnoreCase(type.getSimpleName())) {
			// ArrayList 暂只支持元素为Array的场景
			List<Object[]> templist = (List<Object[]>) obj;
			// 考虑数据为空的场景
			if( 0 == templist.size() ){ cv.isError=true; return cv; }
			cv.values = new Object[templist.size()][templist.get(0).length];
			for(int i = 0; i<templist.size(); i++){
				cv.values[i]=templist.get(i);  
			}

			cv.columnTypes = new int[templist.size()][templist.get(0).length];
			cv.scales = new int[templist.size()][templist.get(0).length];
			for(int i = 0; i<templist.size(); i++){
				for( int j = 0; j<templist.get(i).length; j++ ){
					cv.columnTypes[i][j]= getColumnType(templist.get(i)[j]);  // getColumnType
					cv.scales[i][j]= getScales(templist.get(i)[j]);  // getScales
				}
			}
		} else if (type.isArray()) {
			if ("String[]".equalsIgnoreCase(type.getSimpleName())) {
				String[] arr = (String[]) obj;
				cv.values = new String[][] { arr };

				cv.columnTypes = new int[1][arr.length];
				cv.scales = new int[1][arr.length];
				for(int i = 0; i<arr.length; i++){
					cv.columnTypes[0][i]= 1;
					cv.scales[0][i]= 0;
				}
			} else if ("String[][]".equalsIgnoreCase(type.getSimpleName())) {
				String[][] arr = (String[][]) obj;
				cv.values = arr;

				if( 0 == arr[0].length ){ cv.isError=true; return cv; }
				cv.columnTypes = new int[arr.length][arr[0].length];
				cv.scales = new int[arr.length][arr[0].length];
				for(int  i = 0; i<arr.length; i++){
					for(int j = 0; j<arr[0].length; j++){
						cv.columnTypes[i][j]= 1;
						cv.scales[i][j]= 0;
					}
				}
			}else if ("Integer[]".equalsIgnoreCase(type.getSimpleName())) {
				Integer[] arr = (Integer[]) obj;
				cv.values = new Integer[][] { arr };

				cv.columnTypes = new int[1][arr.length];
				cv.scales = new int[1][arr.length];
				for(int i = 0; i<arr.length; i++){
					cv.columnTypes[0][i]= 3;
					cv.scales[0][i]= 0;
				}
			} else if ("Integer[][]".equalsIgnoreCase(type.getSimpleName())) {
				Integer[][] arr = (Integer[][]) obj;
				cv.values = arr;

				if( 0 == arr[0].length ){ cv.isError=true; return cv; }
				cv.columnTypes = new int[arr.length][arr[0].length];
				cv.scales = new int[arr.length][arr[0].length];
				for(int  i = 0; i<arr.length; i++){
					for(int j = 0; j<arr[0].length; j++){
						cv.columnTypes[i][j]= 3;
						cv.scales[i][j]= 0;
					}
				}
			} else if ("Object[]".equalsIgnoreCase(type.getSimpleName())) {
				Object[] arr = (Object[]) obj;
				cv.values = new Object[][] { arr };

				cv.columnTypes = new int[1][arr.length];
				cv.scales = new int[1][arr.length];
				for(int i = 0; i<arr.length; i++){
					cv.columnTypes[0][i]= getColumnType(arr[i]);
					cv.scales[0][i]= getScales(arr[i]);
				}
			} else if ("Object[][]".equalsIgnoreCase(type.getSimpleName())) {
				Object[][] arr = (Object[][]) obj;
				cv.values = arr;

				if( 0 == arr[0].length ){ cv.isError=true; return cv; }
				cv.columnTypes = new int[arr.length][arr[0].length];
				cv.scales = new int[arr.length][arr[0].length];
				for(int  i = 0; i<arr.length; i++){
					for(int j = 0; j<arr[0].length; j++){
						cv.columnTypes[i][j]= getColumnType(arr[i][j]);
						cv.scales[i][j]= getScales(arr[i][j]);
					}
				}
			}
		}

		System.out.println("init userdefined cell finish!");
		
		return cv;
	}

	/**
	 * 
	 * 判断是否是 变量
	 * <p>
	 * the value has been value.trim().replaceAll("  ", " ")
	 * @param value
	 * @return
	 */
	public boolean isVar(String value){
		value = value.trim().replaceAll(" ", "");
		if(StringUtils.isEmpty(value)){
			return false;
		}else if(value.toLowerCase().startsWith("${")
				&& value.toLowerCase().endsWith("}")){
			return true;
		}
		return false;
	}
	
	/** judge whether database connection url define
	 * <p>
	 * the value has been value.trim().replaceAll("  ", " ")
	 **/
	public boolean isArrVar(String value){
		value = value.trim().replaceAll("  ", " ");
		if(StringUtils.isEmpty(value)){
			return false;
		}else if(value.toLowerCase().startsWith("${ ")
				&& value.toLowerCase().endsWith("List}")){
			return true;
		}else if(value.toLowerCase().startsWith("${ ")
				&& value.toLowerCase().endsWith("Array}")){
			return true;
		}
		return false;
	}

	/**


	 * @param args
	 */
	public static void main(String[] args) {
		try {

			Excel2Excel2 e2e = new Excel2Excel2();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("str", "aa123");
//			map.put("int3", 124);
//			map.put("date", new Date());
//			
//			List list = new ArrayList();
//			list.add(map);
//			list.add(map);
//			map.put("list", list);
//
//
//			List<Map> list2 = new ArrayList<Map>();
//			list2.add(map);
//			list2.add(map);
//			map.put("list2", list2);
//			
//
//			List<MyCellValue2> list3 = new ArrayList<MyCellValue2>();
//			MyCellValue2 tmpCell1 = new MyCellValue2();
//			MyCellValue2 tmpCell2 = new MyCellValue2();
//			list3.add(tmpCell1);
//			list3.add(tmpCell2);
//			map.put("list3", list3);
//			
//			
//			String[] arr1 = new String[]{"str123", "String222"};
//			String[][] arr2 = new String[][]{{"str123", "String222"},{"str1111", "String222"}};
//			
//			Object[] arr11 = new Object[]{123,"String1111"};
//			Object[][] arr22 = new Object[][]{{123,"String1111"},{223,"String222222"}};
//			
//			int[] arr111 = {123,111};
//			int[][] arr222 = {{123,222},{223,222}};
//			
//			
//			map.put("arr1", arr1);
//			map.put("arr2", arr2);
//			map.put("arr11", arr11);
//			map.put("arr22", arr22);
//			map.put("arr111", arr11);
//			map.put("arr222", arr22);
//			
//			Iterator it = map.keySet().iterator();
//			while(it.hasNext() ){
//				Object obj = it.next();
//				System.out.println("################  key:"+ obj +" ################# ");
//				e2e.getUserDefineCell(null, null, map.get(obj));
//			}
			
			
			String inputFile = "G:\\temp\\KPI3-sql.xlsx";
			String outputFile ="G:\\temp\\result.xlsx";
			Map<String, Object> dataMap = new HashMap<String, Object>();
			/* 
			 * ${aList}
			 * ${var1}	${var2}	${var3}	${var4}
			 * ${bArray}
			 * 
			 */
			dataMap.put("var1", "String123445");
			dataMap.put("var2", 8888);
			dataMap.put("var3", new BigDecimal(600.99).setScale(2, BigDecimal.ROUND_HALF_UP) );
			dataMap.put("var4", new java.sql.Date(new Date().getTime()) );
			
			List<Object[]> aList = new ArrayList<Object[]>();
			Object[] arr = new Object[]{123,"col2", 300.22, "col4"};
			aList.add(arr);
			
			Object[][] bArray = new Object[][]{
				{22221,"col22221"},{22222,"col22222"},{22223,"col22223"},
				{22224,"col22224"},{22225,"col22225"},{22226,"col22226"}
				};
			
			dataMap.put( "aList", aList );
			dataMap.put( "bArray", bArray );

			
			//text/plain,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
			e2e.exchangeExcel(inputFile, outputFile, 
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					dataMap );

			System.out.println("all success,no errors!");
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("error:" + e.getLocalizedMessage());
		}finally{

			
		}
	}
}
