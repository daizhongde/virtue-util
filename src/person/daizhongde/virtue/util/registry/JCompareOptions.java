package person.daizhongde.virtue.util.registry;

/**
 * 
 * @Description: 比较选项
 * @author daizd
 * @date 2021年11月19日
 *
 */
public class JCompareOptions implements JOptions {
   private boolean v = false;
   private String valueName;

   private boolean ve = false;

   private boolean s = false;
   private boolean oa = false, od = false, os = false, on = false;

   /**
    * 所选注册表项下的要比较的值的名称。 省略时，该项下的所有值都会得到比较
    * 
    * @param valueName
    */
   public JCompareOptions useV(String valueName) {
      this.v = true;
      this.valueName = valueName;
      this.ve = false;
      return this;
   }

   /**
    * 比较空白值名称的值(默认)
    */
   public JCompareOptions useVE() {
      this.ve = true;
      this.v = false;
      return this;
   }

   /**
    * 比较所有子项和值
    */
   public JCompareOptions useS() {
      this.s = true;
      return this;
   }

   /**
    * 显示所有不同和匹配结果
    * 
    * @return
    */
   public JCompareOptions useOA() {
      this.oa = true;
      this.od = false;
      this.os = false;
      this.on = false;
      return this;
   }

   /**
    * 只显示不同的结果
    * 
    * @return
    */
   public JCompareOptions useOD() {
      this.oa = false;
      this.od = true;
      this.os = false;
      this.on = false;
      return this;
   }

   /**
    * 只显示匹配结果
    * 
    * @return
    */
   public JCompareOptions useOS() {
      this.oa = false;
      this.od = false;
      this.os = true;
      this.on = false;
      return this;
   }

   /**
    * 不显示结果
    * 
    * @return
    */
   public JCompareOptions useON() {
      this.oa = false;
      this.od = false;
      this.os = false;
      this.on = true;
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

      if (this.s) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/s");
      }

      if (this.oa) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/oa");
      }

      if (this.od) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/od");
      }

      if (this.os) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/os");
      }

      if (this.on) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/on");
      }

      return builder.toString();
   }
}