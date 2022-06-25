package person.daizhongde.virtue.util.registry;

/**
 * 
 * @Description:  删除选项
 * @author daizd
 * @date 2021年11月19日
 *
 */
public class JDeleteOptions implements JOptions {
   private boolean v;
   private String valueName;

   private boolean ve;
   private boolean va;

   private boolean f;

   /**
    * 所选项之下要删除的值的名称。省略时，该项下的所有子项的值都会被删除
    * 
    * @param valueName
    */
   public JDeleteOptions useV(String valueName) {
      this.v = true;
      this.valueName = valueName;
      this.ve = false;
      this.va = false;
      return this;
   }

   /**
    * 删除空值名称的值(默认)
    * 
    * @return
    */
   public JDeleteOptions useVE() {
      this.ve = true;
      this.v = false;
      this.va = false;
      return this;
   }

   /**
    * 删除该项下面的所有值
    * 
    * @return
    */
   public JDeleteOptions useVA() {
      this.va = true;
      this.v = false;
      this.ve = false;
      return this;
   }

   /**
    * 不用提示，强制删除
    * 
    * @return
    */
   public JDeleteOptions useF() {
      this.f = true;
      return this;
   }

   @Override
   public String toOptions() {
      StringBuilder builder = new StringBuilder();
      if (this.v && this.valueName != null && this.valueName.length() > 0) {
         builder.append("/v ").append(this.valueName);
      }
      if (this.ve) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/ve");
      }

      if (this.va) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/va");
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