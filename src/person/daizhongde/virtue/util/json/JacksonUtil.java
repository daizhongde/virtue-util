package person.daizhongde.virtue.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The class JacksonUtil
 *
 * json字符与对像转换
 * @author daizd
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public final class JacksonUtil {
//	   /**
//	    * 获取泛型的Collection Type
//	    * @param jsonStr json字符串
//	    * @param collectionClass 泛型的Collection
//	    * @param elementClasses 元素类型
//	    */
//	   @SuppressWarnings("deprecation")
//	   private static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
//	          ObjectMapper mapper = new ObjectMapper();
//
////	          JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
//	          JavaType javaType = mapper.getTypeFactory().constructParametrizedType(collectionClass,collectionClass, elementClasses);
//	          
//	          return mapper.readValue(jsonStr, javaType);
//
//	   }

		public static ObjectMapper objectMapper;

		/**
		 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
		 * (1)转换为普通JavaBean：readValue(json,Student.class)
		 * (2)转换为List,如List<Student>,将第二个参数传递为Student
		 * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
		 * 
		 * @param jsonStr
		 * @param valueType
		 * @return
		 */
		public static <T> T readValue(String jsonStr, Class<T> valueType) {
			if (objectMapper == null) {
				objectMapper = new ObjectMapper();
			}

			try {
				return objectMapper.readValue(jsonStr, valueType);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
		
		/**
		 * json数组转List
		 * @param jsonStr
		 * @param valueTypeRef
		 * @return
		 */
		public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
			if (objectMapper == null) {
				objectMapper = new ObjectMapper();
			}

			try {
				return objectMapper.readValue(jsonStr, valueTypeRef);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * 把JavaBean转换为json字符串
		 * 
		 * @param object
		 * @return
		 */
		public static String toJSon(Object object) {
			if (objectMapper == null) {
				objectMapper = new ObjectMapper();
			}

			try {
				return objectMapper.writeValueAsString(object);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

}
