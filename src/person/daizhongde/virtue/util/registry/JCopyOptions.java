package person.daizhongde.virtue.util.registry;


/**
 * 
 * @Description: 复制选项
 * @author daizd
 * @date 2021年11月19日
 *
 */
public class JCopyOptions implements JOptions {
   private boolean s;
   private boolean f;

   /**
    * 复制所有子项和值
    * 
    * @return
    */
   public JCopyOptions useS() {
      this.s = true;
      return this;
   }

   /**
    * 不用提示，强制复制
    * 
    * @return
    */
   public JCopyOptions useF() {
      this.f = true;
      return this;
   }

   @Override
   public String toOptions() {
      StringBuilder builder = new StringBuilder();
      if (this.s) {
         builder.append("/s");
      }

      if (this.f) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/f");
      }

      return builder.toString();
   }
}