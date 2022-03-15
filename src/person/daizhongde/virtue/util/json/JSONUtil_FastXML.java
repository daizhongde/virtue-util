//package person.daizhongde.virtue.util.json;
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.fasterxml.jackson.core.JsonEncoding;
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//
///**
// * 将Java对象转换成json字符串，也可以将json字符串转换成Java对象
// * @author zhaojd
// *
// */
//public class JSONUtil_FastXML {
//
//    private JsonGenerator jsonGenerator = null;
//    private ObjectMapper objectMapper = null;
//
//    @SuppressWarnings("deprecation")
//    @Before
//    public void init() {
//        objectMapper = new ObjectMapper();
//        try {
//            jsonGenerator =
//                    objectMapper.getJsonFactory()
//                            .createJsonGenerator(System.out, JsonEncoding.UTF8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void destory() {
//        try {
//            if (jsonGenerator != null) {
//                jsonGenerator.flush();
//            }
//            if (!jsonGenerator.isClosed()) {
//                jsonGenerator.close();
//            }
//            jsonGenerator = null;
//            objectMapper = null;
//            System.gc();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 1.1 java对象转换成json 
//     */
//    @Test
//    public void javaBeanToJson(Object bean) throws Exception {
//        try {
//            System.out.println("jsonGenerator");
//            /**
//             * writeObject()可以转换Java对象；例如：JavaBean/Map/List/Array等
//             */
//            jsonGenerator.writeObject(bean);
//            System.out.println();
//
//            System.out.println("ObjectMapper");
//            /**
//             * writeValue() 和 writeObject() 是相同的功能
//             */
//            objectMapper.writeValue(System.out, bean);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 所谓的json格式：
//     *     {"键":"值","键":"值","键":"值"}
//     * jsonGenerator
//     *  {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null}
//     *  ObjectMapper
//     *  {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null}
//     */
//
//    /**
//     * 1.2 将Map集合转换成json字符串
//     * @throws Exception
//     */
//    @Test
//    public void mapToJson(Object bean) throws Exception {
//        try {
//            Map<String, Object> map = new HashMap<String, Object>();
//          
//
//            System.out.println("jsonGenerator");
//            jsonGenerator.writeObject(map);
//            System.out.println("");
//
//            System.out.println("objectMapper");
//            objectMapper.writeValue(System.out, map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * jsonGenerator
//     *  {
//     *   "account2":{"address":"china-Lanzhou","name":null,"id":0,"email":"zhaojd@qq.com","birthday":null},
//     *   "name":"zhaojd",
//     *   "account":{"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null}
//     *  }
//     *  objectMapper
//     *  {
//     *   "account2":{"address":"china-Lanzhou","name":null,"id":0,"email":"zhaojd@qq.com","birthday":null},
//     *   "name":"zhaojd","
//     *   account":{"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null}
//     *  }
//     */
//
//    /**
//     * 1.3 将List转换成json
//     */
//    @Test
//    public void listToJson(Object bean) throws Exception {
//        try {
//            List list = new ArrayList();
//            
//            list.add(bean);
//            System.out.println("jsonGenerator");
//            jsonGenerator.writeObject(list);
//            System.out.println();
//            System.out.println("ObjectMapper");
//            /**
//             * 用objectMapper直接返回list转换成的JSON字符串
//             */
//            System.out.println("直接返回list转换成的json" + objectMapper.writeValueAsString(list));
//            //objectMapper list转换成JSON字符串
//            objectMapper.writeValue(System.out, list);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * jsonGenerator
//     *  [
//     *      {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null},
//     *      {"address":"address2","name":"name2","id":2,"email":"email2","birthday":null}
//     *  ]
//     *  ObjectMapper
//     *  直接返回list转换成的json[
//     *      {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null},
//     *      {"address":"address2","name":"name2","id":2,"email":"email2","birthday":null}
//     *  ]
//     *  [
//     *      {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null},
//     *      {"address":"address2","name":"name2","id":2,"email":"email2","birthday":null}
//     *  ]
//     */
//
//    /**
//     * 1.4 下面来看看jackson提供的一些类型，用这些类型完成json转换；如果你使用这些类型转换JSON的话，
//     *     那么你即使没有JavaBean(Entity)也可以完成复杂的Java类型的JSON转换。
//     *     下面用到这些类型构建一个复杂的Java对象，并完成JSON转换。
//     */
//    @Test
//    public void othersToJson(Object bean) throws Exception {
//        try {
//            String[] arr = {"a", "b", "c"};
//            System.out.println("jsonGenerator");
//            String str = "hello world jackson!";
//            //byte
//            jsonGenerator.writeBinary(str.getBytes());
//            //boolean
//            jsonGenerator.writeBoolean(true);
//            //null
//            jsonGenerator.writeNull();
//            //float
//            jsonGenerator.writeNumber(2.2f);
//            //char
//            jsonGenerator.writeRaw("c");
//            //String
//            jsonGenerator.writeRaw(str, 5, 10);
//            //String
//            jsonGenerator.writeRawValue(str, 5, 5);
//            //String
//            jsonGenerator.writeString(str);
//            jsonGenerator.writeTree(JsonNodeFactory.instance.pojoNode(str));
//            System.out.println();
//
//            //Object
//            jsonGenerator.writeStartObject();//{
//            jsonGenerator.writeObjectFieldStart("user");//user:{
//            jsonGenerator.writeStringField("name", "jackson");//name:jackson
//            jsonGenerator.writeBooleanField("sex", true);//sex:true
//            jsonGenerator.writeNumberField("age", 22);//age:22
//            jsonGenerator.writeEndObject();//}
//
//            jsonGenerator.writeArrayFieldStart("infos");//infos:[
//            jsonGenerator.writeNumber(22);//22
//            jsonGenerator.writeString("this is array");//this is array
//            jsonGenerator.writeEndArray();//]
//
//            jsonGenerator.writeEndObject();//}
//
//
//            //complex Object
//            jsonGenerator.writeStartObject();//{
//            jsonGenerator.writeObjectField("user", bean);//user:{bean}
//            jsonGenerator.writeObjectField("infos", arr);//infos:[array]
//            jsonGenerator.writeEndObject();//}
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * jsonGenerator
//     *  "aGVsbG8gd29ybGQgamFja3NvbiE=" true null 2.2c world jac  worl "hello world jackson!" "hello world jackson!"
//     *   {"user":{"name":"jackson","sex":true,"age":22},"infos":[22,"this is array"]} 
//     *   {"user":{"address":"address","name":"haha","id":1,"email":"email","birthday":null},"infos":["a","b","c"]}
//     */
//
////	    /**
////	     * 2.1 将json字符串转换成JavaBean对象
////	     */
////	    @Test
////	    public void  jsonToJavaBean( Class<T> valueType) throws Exception {
////	        String json = "{\"name\":\"zhaojd\",\"email\":\"email\",\"address\":\"address\"}";
////	        try {
////	        	Object acc = objectMapper.readValue(json, valueType);
//////	            System.out.println(acc.getName());
////	            System.out.println(acc);
////	        } catch (JsonParseException e) {
////	            e.printStackTrace();
////	        } catch (JsonMappingException e) {
////	            e.printStackTrace();
////	        } catch (IOException e) {
////	            e.printStackTrace();
////	        }
////	    }
//
//    /**
//     * zhaojd
//     * 0#zhaojd#email#address#null
//     */
//
//    /**
//     * 2.2 将json 转换成List
//     */
//    @SuppressWarnings("unchecked")
//    @Test
//    public void jsonToList() {
//        String json =
//                "[{\"address\": \"address2\",\"name\":\"zhaojd\",\"id\":2,\"email\":\"email2\"},"
//                        + "{\"address\":\"address\",\"name\":\"zhaojd\",\"id\":1,\"email\":\"email\"}]";
//        try {
//            List<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, List.class);
//            System.out.println("list的长度:" + list.size());
//            for (int i = 0; i < list.size(); i++) {
//                Map<String, Object> map = list.get(i);
//                Set<String> set = map.keySet();
//                for (Iterator<String> it = set.iterator(); it.hasNext();) {
//                    String key = it.next();
//                    System.out.println(key + ":" + map.get(key));
//                }
//            }
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *   list的长度：2
//     *   address:address2
//     *   name:zhaojd
//     *   id:2
//     *   email:email2
//     *   address:address
//     *   name:zhaojd
//     *   id:1
//     *   email:email
//     */
//    /**
//     * 2.3 json字符串转换成Array
//     */
//    @Test
//    public void jsonToArray() throws Exception {
//        String json =
//                "[{\"address\": \"address2\",\"name\":\"zhaojd2\",\"id\":2,\"email\":\"email2\"},"
//                        + "{\"address\":\"address\",\"name\":\"zhaojd\",\"id\":1,\"email\":\"email\"}]";
//        try {
//            Object[] arr = objectMapper.readValue(json, Object[].class);
//            System.out.println("数组的长度为：" + arr.length);
//            for (int i = 0; i < arr.length; i++) {
//                System.out.println(arr[i]);
//            }
//
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     *   数组的长度为：2
//     *   2#zhaojd2#email2#address2#null
//     *   1#zhaojd#email#address#null
//     */
//
//    /**
//     * 2.4 json转换成Map集合
//     */
//    @SuppressWarnings("unchecked")
//    @Test
//    public void jsonToMap() throws Exception {
//        String json =
//                "{\"success\":true,\"A\":{\"address\": \"address2\",\"name\":\"zhaojd2\",\"id\":2,\"email\":\"email2\"},"
//                        + "\"B\":{\"address\":\"address\",\"name\":\"zhaojd\",\"id\":1,\"email\":\"email\"}}";
//        try {
//            Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
//            System.out.println("map集合的长度为：" + maps.size());
//            Set<String> key = maps.keySet();
//            Iterator<String> iter = key.iterator();
//            while (iter.hasNext()) {
//                String field = iter.next();
//                System.out.println(field + ":" + maps.get(field));
//            }
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    /*
//     *   map集合的长度为：3
//     *   success:true
//     *   A:{address=address2, name=zhaojd2, id=2, email=email2}
//     *   B:{address=address, name=zhaojd, id=1, email=email}
//     */
//    /**
//     * 3. jackson对xml的支持
//     */
//    @Test
//    public void objectToXml() throws Exception {
//        System.out.println("XmlMapper");
//        XmlMapper xml = new XmlMapper();
//
//        try {
//            /**
//             * javaBean转换成xml
//             */
//           
//            String xmlStr = xml.writeValueAsString(new Object());
//            System.out.println(xmlStr);
//            /**
//             * List转换成xml
//             */
//            List list = new ArrayList();
//        
//            System.out.println(xml.writeValueAsString(list));
//
//            /**
//             * Map转换xml文档
//             */
//            Map<String, String> map = new HashMap<String, String>();
//      
//            System.out.println(xml.writeValueAsString(map));
//        } catch (JsonGenerationException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * XmlMapper
//        <AccountBean xmlns="">
//            <id>1</id>
//            <name>zhaojd</name>
//            <email>mr_zhaojd</email>
//            <address>广州</address>
//            <birthday><birthday>1992-08</birthday></birthday>
//        </AccountBean>
//        <ArrayList xmlns="">
//            <id>1</id>
//            <name>zhaojd</name>
//            <email>mr_zhaojd</email>
//            <address>广州</address>
//            <birthday><birthday>1992-08</birthday></birthday>
//         </ArrayList>
//         <zdef532234490:ArrayList xmlns:zdef532234490="">
//             <zdef532234490:id>1</zdef532234490:id>
//             <zdef532234490:name>zhaojd</zdef532234490:name>
//             <zdef532234490:email>mr_zhaojd</zdef532234490:email>
//             <zdef532234490:address>广州</zdef532234490:address>
//             <zdef532234490:birthday><zdef532234490:birthday>1992-08</zdef532234490:birthday></zdef532234490:birthday>
//         </zdef532234490:ArrayList>
//        <HashMap xmlns="">
//            <A>
//                <id>1</id>
//                <name>zhaojd</name>
//                <email>mr_zhaojd</email>
//                <address>广州</address>
//                <birthday><birthday>1992-08</birthday></birthday>
//            </A>
//            <B>
//                <id>1</id>
//                <name>zhaojd</name>
//                <email>mr_zhaojd</email>
//                <address>广州</address>
//                <birthday><birthday>1992-08</birthday></birthday>
//            </B>
//        </HashMap>
//     */
//
//    @Test
//    public void xmlToObject() {
//        XmlMapper xmlMapper = new XmlMapper();
//        String xml = "<AccountBean><id>1</id><name>zhaojd</name><email>mr_zhaojd</email><address>广州</address><birthday><birthday>1992-08</birthday></birthday></AccountBean>";
//        try {
//            Object ab = xmlMapper.readValue(xml,Object.class);
//            System.out.println(ab);
//        }  catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * 1#zhaojd#mr_zhaojd#广州#1992-08
//     */
//}
