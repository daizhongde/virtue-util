package person.daizhongde.virtue.util.db;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class MySQLColumnType extends BaseDao{
	//sql_oralce
	private String sql_1 = "select JKSBH 缴款书编号,  XMNM 收费项目内码, XMBM 收费项目编码, XMMC 收费项目名称, CZQHNM 财政区划内码,   QHMC 财政区划名称, DTNM 大厅内码,         DTBM 大厅编码,         DTMC 大厅名称,    DWNM 执收单位内码,DWBM 执收单位编码, DWMC 执收单位名称, KPRQ 开票日期,     JKR 缴款人,        JKRKHH 缴款人开户银行, JKRZH 缴款人帐号,  YHNM 收款人开户行内码, YHMC 收款人开户行名称, SHRZH 收款人帐号,  JLDW 计量单位,SL 数量,           JE 金额,          SFBZ 收费标准,     ZJE 总金额,decode(JMBZ,0,'正常',1, '减征',   2,'集中汇缴', '' ) 减免标志,decode(ZFBZ,0,'未作废',1, '已作废', '' ) 作废标志,decode(DYBZ,0,'未打印',1, '已打印', '' ) 打印标志,decode(QRBZ,0,'未确认',1, '已确认', '' ) 刷卡确认标志,KPR 开票人,decode(HZBZ,0,'未回执',1, '已回执', '' ) 回执标志, HZR 回执人,HZRQ 回执日期,decode(KPFS,0,'手工开票',1, '电脑开票', '' ) 开票方式,decode(JKLX,0,'现金缴款',1, '减征',2,'POS刷卡',3,'批扣',4, '单位开单',5,'网银支付',6,'转帐',9, '未知','' ) 缴款类型,DWYWLSH 单位业务流水号, YHJYLSH 银行交易流水号,decode(JXZT,0,'未缴销',1, '已缴销',2,'核销申请',3,'核销', '' ) 缴销状态,decode(PJZT,0,'正常',1, '退付',2, '调整', '' ) 票据状态,decode(CSBZ,0,'未传输',1, '已传输', '' ) 传输标志,decode(YSBZ,0,'是',1, '否', '' ) 预收标志,    decode(CZJZBZ,0,'财政未记帐',1, '财政已记帐', '' ) 财政记帐标志,CZJZSJ 财政记帐时间,decode(SFJZ,0,'已记帐',1, '未记帐', '' ) 记帐标志,JZSJ 记帐时间,SHH 商户号,ZDH 终端号,XTCKH 系统参考号,YHSKSJ 银行收款时间, PJJYM 票据校验码,decode(SFTZBZ,0,'未调整',1, '已调整', '' ) 调整标志,   BZ 备注,SCSJ 上传时间,SKRMC 收款人名称,YLLSH 银联交易流水号 from t_srhs_jks t ";
	//sql_sqlserver
	private String sql_2 = "select * from t_columntype";
	//sql_mysql
	private String sql_3 = "select * from t_columntype";
	
	
	public static void main(String args[]) throws Exception{
		MySQLColumnType t = new MySQLColumnType();
		//cpab gzpost tool
		String owner = "test";
//t_q_main   t_q_detail  
		String tableName = "t_hospital";//for comment query
		String tableNameWithUSER = owner+"."+tableName;//for data query  eg:"cpab.TB_PAYER_COMMI_INFO"
	
		String sql = "select * from "+tableNameWithUSER;
//		String sql = "SELECT CAST(COUNT(*) AS UNSIGNED) num FROM tool.t_authority_user t2 WHERE t2.supervisor_id=1";
//		String sql = FileUtil.readString4("D:/daizd/Workspaces/MyEclipse 2015 CI/virtue-util/src/person/daizhongde/virtue/util/db/select.sql");
		
//		String CommentSQL = "select column_name,comments from user_col_comments where table_name=upper('"+tableName+"')";
		
		String CommentSQL = "select column_name,column_comment " +
				"			   from INFORMATION_SCHEMA.COLUMNS " +
				"             where TABLE_SCHEMA=upper('"+owner+"') and table_name=upper('"+tableName+"')";
		
		Vector[] v =  t.sColumnNamesAndTypes( sql );

		
		//v.length == 6   
//		v[0]:ColumnLabel         v[1]:ColumnName      
//		v[2]:ColumnType          v[3]:ColumnTypeName      
//		v[4]:Precision 有效位数 ,精度      v[5]: Scale 小数点后位数
//		for(int m=0, n=v.length; m<n; m++){
//			Vector v2 =  v[m];
//			for(int i=0, j=v2.size(); i<j; i++){
//				System.out.print("("+i+") "+v2.get(i)+"  |");
//			}
//			System.out.println("");
//		}

		Vector ColumnLabel =  v[0];
		Vector ColumnName =  v[1];
		Vector ColumnType =  v[2];
		Vector ColumnTypeName =  v[3];
		Vector Precision =  v[4];
		Vector Scale =  v[5];
		
//		print(ColumnLabel);	print(ColumnName);
		print(ColumnType);	
//		print(ColumnTypeName);
//		print(Precision);	print(Scale);
		
//		printDefaultColumns(ColumnLabel);	
		
//		printMap(ColumnLabel,ColumnLabel);	printMap(ColumnLabel,ColumnName);
		printMap(ColumnLabel,ColumnType);	
//		printMap(ColumnLabel,ColumnTypeName);
//		printMap(ColumnLabel,Precision);	printMap(ColumnLabel,Scale);

		Map Comment = t.selectMapValue(CommentSQL); 
		
//		printMap(ColumnLabel,ColumnType,Precision,Scale);
//		printMap(ColumnLabel,ColumnType,Precision,Scale,ColumnTypeName);
		
		Vector front = new Vector();
		Vector back = new Vector();
		
		/*  for field use-- Copycat */
		printFieldMapCopycat(ColumnLabel,
				ColumnType,ColumnTypeName,Precision,Scale,Comment,
				front, back );
		
		/*  for field use-- Standard */
//		printFieldMap(ColumnLabel,
//				ColumnType,ColumnTypeName,Precision,Scale,Comment,
//				front,back );
		
		
		/*  for export use */
		printExportMap(ColumnLabel,ColumnType,Precision,Scale,ColumnTypeName, Comment);
		
		//no column alias
		printSelectSQLNoTBAlias1(ColumnName, owner+"."+tableName);
		printSelectSQLWithTBAlias1(ColumnName, owner+"."+tableName);
		
		//with column alias
		System.out.println("\nbelow sql with column alias:");
//		printSelectSQLWithColAliasNotableAlias(ColumnName, owner+"."+tableName);
		printSelectSQLWithColAliasCopycat1(ColumnName, owner+"."+tableName);
		printSelectSQLWithColAlias1(ColumnName, owner+"."+tableName);
			
	}
	public  Vector[] sColumnNamesAndTypes() {
//		return selectOnlyNote("select * from [db_MoneyManage].[dbo].[tb_authority_user] where n_id="
//				+ recordNum);
//		return selectColumnNamesAndTypes("SELECT TOP 1000 [n_id] as '哈萨克',[c_number] '骠',[c_password],[c_name],[n_sex],[c_birthday],[c_idcard],[c_mobilenum],[c_mobilenum2],[c_telephoneznum],[c_telephonenum],[c_qq],[c_msn],[c_email],[c_postcode],[c_address],[c_photopath],[n_money],[n_livemoney],[n_cellnum] FROM [db_MoneyManage].[dbo].[tb_authority_user]");
		return selectColumnNamesAndTypes("SELECT TOP 1000 [bigint_id]"+
      ",[binary]"+
      ",[bit]"+
      ",[char]"+
      ",[date]"+
      ",[datetime]"+
      ",[datetime2]"+
      ",[datetimeoffset]"+
      ",[decimal]"+
      ",[float]"+
      ",[geography]"+
      ",[geometry]"+
      ",[hierarchyid]"+
      ",[image]"+
      ",[int]"+
      ",[money]"+
      ",[nchar]"+
      ",[ntext]"+
      ",[numeric]"+
      ",[nvarchar]"+
      ",[nvarchar_m]"+
      ",[real]"+
      ",[smalldatetime]"+
      ",[smallint]"+
      ",[smallmoney]"+
      //",[sql_variant]"+
      ",[text]"+
      ",[time]"+
      ",[timestamp]"+
      ",[tinyint]"+
      ",[uniqueidentifier]"+
      ",[varbinary]"+
      ",[varbinary_m]"+
      ",[varchar]"+
      ",[varchar_m]"+
      ",[xml] "+
  "FROM [db_MoneyManage].[dbo].[test]");
	}
	
	public  Vector[] sColumnNamesAndTypes(String sql) {
//		return selectOnlyNote("select * from [db_MoneyManage].[dbo].[tb_authority_user] where n_id="
//				+ recordNum);
//		return selectColumnNamesAndTypes("SELECT TOP 1000 [n_id] as '哈萨克',[c_number] '骠',[c_password],[c_name],[n_sex],[c_birthday],[c_idcard],[c_mobilenum],[c_mobilenum2],[c_telephoneznum],[c_telephonenum],[c_qq],[c_msn],[c_email],[c_postcode],[c_address],[c_photopath],[n_money],[n_livemoney],[n_cellnum] FROM [db_MoneyManage].[dbo].[tb_authority_user]");
		return selectColumnNamesAndTypes(sql);
	}
	public  Vector[] sColumnNamesAndTypesByTableName(String tname) {
		return selectColumnNamesAndTypes("select * from " + tname);
	}
	public static String convertColumnName(String col){
		return col.substring(0, 3).replaceAll("_", "")+col.substring(3).toLowerCase();
	}

	/** 
	 * 字符串形式打印，“｜”线分隔
	 * @param v
	 */
	public static void print(Vector v){
		StringBuffer sb = new StringBuffer();
		for(int i=0, j = v.size(); i<j; i++){
			if(i==0){
				sb.append(""+v.get(i).toString()+"|");
			}else if(i != j-1){
				sb.append(v.get(i).toString()+ "|");
			}else{
				sb.append(v.get(i).toString()+"");
			}
		}	
		System.out.println(sb);
	}
	/** 
	 * 字符串形式打印 	["PAY_ID","ACC_ID"]
	 * @param v
	 */
	public static void printDefaultColumns(Vector v){
		StringBuffer sb = new StringBuffer();
		for(int i=0, j = v.size(); i<j; i++){
			if(i==0){
				sb.append("[\""+v.get(i).toString()+"\",\"");
			}else if(i != j-1){
				sb.append(v.get(i).toString()+ "\",\"");
			}else{
				sb.append(v.get(i).toString()+"\"]");
			}
		}
		System.out.println("\n#### (JS config) Export.export.DefaultColumns(for export) ####");
		System.out.println("#### (JS config) Import.import.DefaultColumns(for import) ####:\n"+sb);
	}
	
	/** 
	 * 字符串形式打印 	["PAY_ID","ACC_ID"]
	 * @param v
	 */
	public static void printDefaultColumns2(Vector v){
		StringBuffer sb = new StringBuffer();
		for(int i=0, j = v.size(); i<j; i++){
			if(i==0){
				sb.append("["+v.get(i).toString()+",");
			}else if(i != j-1){
				sb.append(v.get(i).toString()+ ",");
			}else{
				sb.append(v.get(i).toString()+"]");
			}
		}
		System.out.println("\n#### (JS config) Export.export.DefaultColumns(for export) ####");
		System.out.println("#### (JS config) Import.import.DefaultColumns(for import) ####:\n"+sb);
	}
	
	public static void printMap(Vector ColumnLabel,Vector value){
		StringBuffer sb = new StringBuffer();
		if( value.get(0)instanceof java.lang.String ){
			for(int i=0, j=ColumnLabel.size(); i<j; i++){
				String k=ColumnLabel.get(i).toString();
				if( k.indexOf("N_")!=-1 || k.indexOf("C_")!=-1 );
				else
					k="\""+k+"\"";
				if(i==0){
					sb.append("{"+k+": \""+value.get(i)+"\",");
				}else if(i == j-1){
					sb.append(k+": \""+value.get(i)+"\"}");
				}else{
					sb.append(k+": \""+value.get(i)+"\",");
				}
			}
		}else{
			for(int i=0, j=ColumnLabel.size(); i<j; i++){
				String k=ColumnLabel.get(i).toString();
				if( k.indexOf("N_")!=-1 || k.indexOf("C_")!=-1 );
				else
					k="\""+k+"\"";
				if(i==0){
					sb.append("{"+k+":"+value.get(i)+",");
				}else if(i == j-1){
					sb.append(k+":"+value.get(i)+"}");
				}else{
					sb.append(k+":"+value.get(i)+",");
				}
			}
		}
		System.out.println(sb);
	}
	public static void printMap(Vector ColumnLabel,
			Vector ColumnType,
			Vector Precision,
			Vector Scale){
		StringBuffer sb = new StringBuffer();
		
		for(int i=0, j=ColumnLabel.size(); i<j; i++){
			String k=ColumnLabel.get(i).toString();
			String value = "\""+ColumnLabel.get(i)+"\","+
					ColumnType.get(i)+","+
					Precision.get(i)+","+
					Scale.get(i);
			
			if( k.indexOf("N_")!=-1 || k.indexOf("C_")!=-1 );
			else
				k="\""+k+"\"";
					
			if(i==0){
				sb.append("{"+k+": ["+value+"],");
			}else if(i == j-1){
				sb.append(k+": ["+value+"]}");
			}else{
				sb.append(k+": ["+value+"],");
			}
		}
		
		System.out.println(sb);
	}
	public static void printMap(Vector ColumnLabel,
			Vector ColumnType,
			Vector Precision,
			Vector Scale,
			Vector ColumnTypeName){
		
		StringBuffer sb = new StringBuffer();
		
		for(int i=0, j=ColumnLabel.size(); i<j; i++){
			String k=ColumnLabel.get(i).toString();
			String value = "\""+ColumnTypeName.get(i)+"\","+
					ColumnType.get(i)+","+
					Precision.get(i)+","+
					Scale.get(i);
			
			if( k.indexOf("N_")!=-1 || k.indexOf("C_")!=-1 );
			else
				k="\""+k+"\"";
					
			if(i==0){
				sb.append("{"+k+": ["+value+"],");
			}else if(i == j-1){
				sb.append(k+": ["+value+"]}");
			}else{
				sb.append(k+": ["+value+"],");
			}
		}
		
		System.out.println(sb);
	}
	public static void printFieldMapCopycat(Vector ColumnLabel,
			Vector ColumnType,
			Vector ColumnTypeName,
			Vector Precision,
			Vector Scale,
			Map Comment,
			Vector front,
			Vector back
			) throws Exception{
		
		StringBuffer sb = new StringBuffer();
				
		for(int i=0, j=ColumnLabel.size(); i<j; i++){
			String k=ColumnLabel.get(i).toString().toLowerCase();
			Object Com = Comment.get( ColumnLabel.get(i).toString() );
			Set set = Comment.keySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				Object o = it.next();
//				System.out.println("o.toString()"+o.toString()+",ColumnLabel.get(i).toString():"+ColumnLabel.get(i).toString());
//				if( o.toString().toLowerCase().contains(
//							ColumnLabel.get(i).toString().toLowerCase()
//						)){
				if( o.toString().toLowerCase().indexOf(
						ColumnLabel.get(i).toString().toLowerCase()
					)!=-1 ){
					Com = Comment.get(o);

					Com = Com.toString().trim().split("[|,.｜，。 ]")[0];
					break;
				}
			}
			
			String value = 
					ColumnType.get(i)+"," +
					Precision.get(i)+","+
					Scale.get(i)+","+
					"\""+Com+"\","+
					
					"\""+
					ColumnLabel.get(i).toString().toLowerCase()+"\"," +//front
					"\""+
					ColumnLabel.get(i).toString().toUpperCase()+"\"," +//back
					i;
			
			if( k.indexOf("N_")!=-1 || k.indexOf("C_")!=-1 );
			else
				k="\""+k+"\"";
					
			if(i==0){
				sb.append("{"+k+": ["+value+"],");
			}else if(i == j-1){
				sb.append(k+": ["+value+"]}");
			}else{
				sb.append(k+": ["+value+"],");
			}
		}
		
		System.out.println("\n#### (JS config) Field(for query and import) Copycat ####:\n"+sb);
	}
	/*  for field use */
//	printFieldMap(ColumnLabel,ColumnType,front,back,ColumnTypeName, Comment);

	public static void printFieldMap(Vector ColumnLabel,
			Vector ColumnType,
			Vector ColumnTypeName,
			Vector Precision,
			Vector Scale,
			Map Comment,
			Vector front,
			Vector back) throws Exception{
		StringBuffer sb = new StringBuffer();
				
		for(int i=0, j=ColumnLabel.size(); i<j; i++){
			String k=ColumnLabel.get(i).toString();
			Object Com = Comment.get( ColumnLabel.get(i).toString() );
			Set set = Comment.keySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				Object o = it.next();
//				System.out.println("o.toString()"+o.toString()+",ColumnLabel.get(i).toString():"+ColumnLabel.get(i).toString());
				if( o.toString().toLowerCase().indexOf(
							ColumnLabel.get(i).toString().toLowerCase()
						)!=-1 ){
					Com = Comment.get(o);

					Com = Com.toString().trim().split("[|,.｜，。 ]")[0];
					break;
				}
			}
			
			String value = "";
			if( ColumnLabel.get(i).toString().length()<4 ){
				value = 
						ColumnType.get(i)+"," +
						Precision.get(i)+","+
						Scale.get(i)+","+
						"\""+Com+"\","+
								
						"\""+
						ColumnLabel.get(i).toString().toLowerCase()+"\"," +
						"\""+
						ColumnLabel.get(i).toString().toUpperCase()+"\"," +
						i;
			}else{
				value = 
						ColumnType.get(i)+"," +
						Precision.get(i)+","+
						Scale.get(i)+","+
						"\""+Com+"\","+
								
						"\""+
						ColumnLabel.get(i).toString().substring(3).toLowerCase()+"\"," +
						"\""+
						ColumnLabel.get(i).toString().substring(0,1).toUpperCase()+
							ColumnLabel.get(i).toString().substring(2,3).toUpperCase()+
							ColumnLabel.get(i).toString().substring(3).toLowerCase()+"\"," +
						i;
			};
			
			if( k.indexOf("N_")!=-1 || k.indexOf("C_")!=-1 );
			else
				k="\""+k+"\"";
					
			if(i==0){
				sb.append("{"+k+": ["+value+"],");
			}else if(i == j-1){
				sb.append(k+": ["+value+"]}");
			}else{
				sb.append(k+": ["+value+"],");
			}
		}
		
		System.out.println("#### (JS config) Field(for query and import) Standard ####:\n"+sb);
	}
	
	/*  for export use */
//	printExportMap(ColumnLabel,ColumnType,Precision,Scale,ColumnTypeName, Comment);
	public static void printExportMap(Vector ColumnLabel,
			Vector ColumnType,
			Vector Precision,
			Vector Scale,
			Vector ColumnTypeName,
			Map Comment){
		StringBuffer sb = new StringBuffer();
		
		for(int i=0, j=ColumnLabel.size(); i<j; i++){
			String k=ColumnLabel.get(i).toString();
			Object Com = Comment.get( ColumnLabel.get(i).toString() );
			Set set = Comment.keySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				Object o = it.next();
//				System.out.println("o.toString()"+o.toString()+",ColumnLabel.get(i).toString():"+ColumnLabel.get(i).toString());
				if( o.toString().toLowerCase().indexOf(
							ColumnLabel.get(i).toString().toLowerCase()
						)!=-1 ){
					Com = Comment.get(o);

					Com = Com.toString().trim().split("[|,.｜，。 ]")[0];
					break;
				}
			}
			
			String value = 
					ColumnType.get(i)+","+
					Precision.get(i)+","+
					Scale.get(i)+","+
					"\""+Com+"\","+i;
			
			if( k.indexOf("N_")!=-1 || k.indexOf("C_")!=-1 );
			else
				k="\""+k+"\"";
					
			if(i==0){
				sb.append("{"+k+": ["+value+"],");
			}else if(i == j-1){
				sb.append(k+": ["+value+"]}");
			}else{
				sb.append(k+": ["+value+"],");
			}
		}
		
		System.out.println("\n#### (JS config) Export.export.ColumnMap ####:\n"+sb);
	}
	/**
	 * no column alias, no table alias
	 * @param ColumnName
	 * @param tableName
	 */
	public static void printSelectSQLNoTBAlias1(Vector ColumnName, String tableName){
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		for(int i=0, j=ColumnName.size(); i<j; i++){
			if(i!=j-1){
				sb.append(ColumnName.get(i).toString()+",");
			}else{
				sb.append(ColumnName.get(i).toString()+" ");
			}
		}
		sb.append("from "+tableName);
		
		System.out.println("\n#### (SQL config) Query.query.NativeSQL No Table Alias ####:\n"+sb);
	}
	/**
	 * with column alias, with table alias
	 * @param ColumnName
	 * @param tableName
	 */
	public static void printSelectSQLWithTBAlias1(Vector ColumnName, String tableName){
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		for(int i=0, j=ColumnName.size(); i<j; i++){
			if(i!=j-1){
				sb.append("t1."+ColumnName.get(i).toString()+",");
			}else{
				sb.append("t1."+ColumnName.get(i).toString()+" ");
			}
		}
		sb.append("from "+tableName+" t1");
		
		System.out.println("#### (SQL config)  Query.query.NativeSQL With Table Alias ####:\n"+sb);
	}
	
	public static void printSelectSQLWithColAliasNotableAlias(Vector ColumnName, String tableName){
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		for(int i=0, j=ColumnName.size(); i<j; i++){
			if(i!=j-1){
				sb.append(ColumnName.get(i).toString()+" \""+ColumnName.get(i).toString().toLowerCase()+"\",");
			}else{
				sb.append(ColumnName.get(i).toString()+" \""+ColumnName.get(i).toString().toLowerCase()+"\" ");
			}
		}
		sb.append("from "+tableName);
		
		System.out.println("#### (SQL config) Query.query.NativeSQL No table Alias ####:\n"+sb);
	}

	public static void printSelectSQLWithColAliasCopycat1(Vector ColumnName, String tableName){
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		for(int i=0, j=ColumnName.size(); i<j; i++){
			if(i!=j-1){
				sb.append("t1."+ColumnName.get(i).toString()+" \""+
				ColumnName.get(i).toString().toLowerCase()+"\",");
			}else{
				sb.append("t1."+ColumnName.get(i).toString()+" \""+
				ColumnName.get(i).toString().toLowerCase()+"\" ");
			}
		}
		sb.append("from "+tableName+" t1");
		
		System.out.println("#### (SQL config)  Query.query.NativeSQL With Column Alias #### copycat:\n"+sb);
	}
	
	public static void printSelectSQLWithColAlias1(Vector ColumnName, String tableName){
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		for(int i=0, j=ColumnName.size(); i<j; i++){
			if( ColumnName.get(i).toString().length()<4 ){
				if(i!=j-1){
					sb.append("t1."+ColumnName.get(i).toString()+" \""+
					ColumnName.get(i).toString().toLowerCase()+"\",");
				}else{
					sb.append("t1."+ColumnName.get(i).toString()+" \""+
					ColumnName.get(i).toString().toLowerCase()+"\" ");
				}
			}else{
				if(i!=j-1){
					sb.append("t1."+ColumnName.get(i).toString()+" \""+
					ColumnName.get(i).toString().substring(3).toLowerCase()+"\",");
				}else{
					sb.append("t1."+ColumnName.get(i).toString()+" \""+
					ColumnName.get(i).toString().substring(3).toLowerCase()+"\" ");
				}
			}
		}
		sb.append("from "+tableName+" t1");
		
		System.out.println("#### (SQL config)  Query.query.NativeSQL With Column Alias ####:\n"+sb);
	}

}
