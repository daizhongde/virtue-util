package person.daizhongde.virtue.util.json;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.parser.Feature;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The class JacksonUtil
 * <p>
 * json字符与对像转换
 *
 * @author daizd
 * @version: 1.1
 * @date 2015-09-12
 * @modify by  daizd
 * @modify date  20180302
 */
public final class JacksonUtil {

    public static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
    	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 字符串 转 Java对象
     * <p>
     * com.fasterxml.jackson.databind.ObjectMapper 实现
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
     * 字符串 转 Java对象
     * <p>
     * com.alibaba.fastjson.JSON 实现
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T readValue2(String jsonStr, Class<T> valueType) {
        return JSON.parseObject(jsonStr, valueType);

    }

    /**
     * 字符串 转 Java对象
     * <p>
     * com.fasterxml.jackson.databind.ObjectMapper<br/>
     * com.fasterxml.jackson.core.type.TypeReference 实现
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
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
     * 字符串 转 Java对象
     *
     * <p>
     * com.alibaba.fastjson.parser.Feature<br/>
     * com.alibaba.fastjson.TypeReference<br/>
     * com.alibaba.fastjson.JSON 实现
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public static <T> T readValue2(String jsonStr, TypeReference<T> valueTypeRef) throws JsonParseException, JsonMappingException, IOException {
//        Feature[] ob = new Feature[]{Feature.IgnoreNotMatch, Feature.AllowSingleQuotes};
//        return JSON.parseObject(jsonStr, valueTypeRef, ob );
    	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(jsonStr, valueTypeRef);
    }

    /**
     * Java对象 转 字符串
     *
     *
     * <p>
     * com.fasterxml.jackson.databind.ObjectMapper实现<br/>
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

    /**
     * Java对象 转 字符串
     *
     * <p>
     * com.alibaba.fastjson.JSON实现<br/>
     *
     * @param object
     * @return
     */
    public static String toJSon2(Object object) {
        return JSON.toJSONString(object);
    }

    public static void main(String[] args) throws Exception {
//		destMap = mapper.readValue(jsonData, new TypeReference<Map<Integer, RbtCounter>>(){});


    }
}
