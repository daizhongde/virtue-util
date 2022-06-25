package person.daizhongde.virtue.util.registry;

/**
 * 
 * @Description: 执行结果
 * @author daizd
 * @date 2021年11月19日
 *
 */
public class JExecResult {
   private final int exitValue;
   private boolean success;
   private final String[] lines;

   public JExecResult(int exitValue, String[] lines) {
      this.exitValue = exitValue;
      this.success = exitValue == 0;
      this.lines = lines;
   }

   public int getExitValue() {
      return exitValue;
   }

   public boolean isSuccess() {
      return success;
   }

   public String[] getLines() {
      return lines;
   }

   protected void setSuccess(boolean success) {
      this.success = success;
   }
}