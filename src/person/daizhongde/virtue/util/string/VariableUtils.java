package person.daizhongde.virtue.util.string;

import java.util.HashSet;
import java.util.Set;

public class VariableUtils {
/**
 * SELECT COUNT(1) FROM party.cm_party_@{$TENANT_ID$}    where REGION_ID is null;
 * @param s
 * @return
 */
	public static Set<String> varStatistics( String s ){
		Set<String> hashset=new HashSet<String>();;
		if( null==s || "".equalsIgnoreCase(s) ){
			return hashset;
		}
		char[] b = s.toCharArray();
		String param="";
		boolean paramFlag = false;
		boolean paramBeg = false;
		
		for(char e : b){
			if( e=='@' ){
				paramFlag = true;
				param="";
			}else if(e=='{' && paramFlag){
				paramBeg = true;//init
				param="";
			}else if(e =='}' && paramBeg ){
				hashset.add(param);
				paramBeg = false;
				paramFlag = false;
			}else if(paramBeg){
					param+=e;
			}
		}
		return hashset;
	}
	/**
	 * $TENANT_ID$=[21];$ACCT_ID$=[0..9]
	 * @param s
	 * @return
	 */
	public static Set<String> readVariables( String s ){
		Set<String> hashset=new HashSet<String>();;
		if( null==s || "".equalsIgnoreCase(s) ){
			return hashset;
		}
		String[] arr = s.split("\\;");
		for(String var : arr ){
			hashset.add( var.split("\\=")[0] );
		}
		return hashset;
	}
	
	public static void main(String[] args){
		String s = "$TENANT_ID$=[21];$ACCT_ID$=[0..9]";
		Set<String> hashset= readVariables( s );
		for(String var : hashset ){
			System.out.println("var:"+var);
		}
	}
}
