//package person.daizhongde.virtue.util.registry;
//
//import org.junit.Test;
//
///**
// * 
// * @Description: 注册表测试
// * @author daizd
// * @date 2021年11月19日
// *
// */
//public class JRegistryTest {
//   @Test
//   public void query() throws Exception {
//      JExecResult result = JRegistry.query("HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft",
//            new JQueryOptions().useF("\"Java Development Kit\""));
//      JRegistry.dump(result);
//   }
//   
//
//   @Test
//   public void add() throws Exception {
//      JExecResult result = JRegistry.add("HKEY_LOCAL_MACHINE\\SOFTWARE\\DandSoft",
//            new JAddOptions() );
//      JRegistry.dump(result);
//   }
//}