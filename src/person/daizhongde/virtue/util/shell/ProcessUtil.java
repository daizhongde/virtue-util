package person.daizhongde.virtue.util.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.ArrayUtils;

public class ProcessUtil {
    public static String runShell(String[] shStrArr) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("-----------result--------------");
        sb.append("\n");
        Process process = null;
        //List<String> processList = new ArrayList<String>(); 多命令
        try {
            if(!ArrayUtils.isEmpty(shStrArr)) {
                String sh = "";
                for(int i = 0;i < shStrArr.length;i++) {
                    String tmpSh = shStrArr[i];
                    sh += tmpSh;
                    if(i != shStrArr.length - 1) {
                        sh += "&&";   //网上说多条命令使用这个符号连起来
                    }
                }
                process = Runtime.getRuntime().exec(sh);
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(),"gb2312"));
                String line = "";
                while ((line = input.readLine()) != null) {
                    //processList.add(line);
                    sb.append(line);
                    sb.append("\n");
                }
                process.waitFor();  
                input.close();
            }
            
        } catch (Exception e) {
            //e.printStackTrace();
            sb.append(e.getCause().getMessage());
        }
        /*if(CollectionUtils.isNotEmpty(processList)) {
            
            for (String line : processList) {
                //System.out.println(line);
                sb.append(line);
                sb.append("\n");
            }
        }*/
        
        return sb.toString();
    }
    
    public static void main(String args[]){
    	 String rs = "";
    	 String shellComand = "cmd.exe /c dir D:\\java项目\\docStory转xlsUS\\docStory2xlsUS\\word2excel";
         try{
             String[] shArr = new String[1];
             shArr[0] = shellComand;
             rs = ProcessUtil.runShell(shArr);

         }catch(Exception e){
             rs ="操作失败，请稍后重试!";
             e.printStackTrace();
         }
         System.out.println("rs:"+rs);
    }
}