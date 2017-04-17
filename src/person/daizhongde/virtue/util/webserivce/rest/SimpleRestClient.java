package person.daizhongde.virtue.util.webserivce.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @title：基于jdk的spring的RestTemplate
 * @author：daizd
 * @date：2017-02-06 09:35
 * <P>
 * 这个类日前没有用到
 */
@Component
@Lazy(false)
public class SimpleRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRestClient.class);

    private static RestTemplate restTemplate;

    static {
    	/*-- 连接超时为5秒钟，调用返回超时为 1天  24*60*60 000=86400000 --*/
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setReadTimeout(86400000);
        requestFactory.setReadTimeout(60000);
        requestFactory.setConnectTimeout(5000);

        // 添加转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
//        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        LOGGER.info("SimpleRestClient初始化完成");
    }

    private SimpleRestClient() {

    }

    @PostConstruct
    public static RestTemplate getClient() {
        return restTemplate;
    }

}