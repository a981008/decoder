package com.wang.decoder.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.decoder.Decoder;
import com.wang.decoder.annotation.DecodeRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 统一参数处理
 *
 * @author Wang
 * @since 2022/7/27
 */
@RestControllerAdvice
public class DecodeRequestBodyAdvice extends RequestBodyAdviceAdapter {
    @Autowired
    private Decoder decoder;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(DecodeRequestBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        // 读取加密的请求体
        byte[] body = new byte[inputMessage.getBody().available()];
        inputMessage.getBody().read(body);

        // 拿到注解标识的字段解密
        DecodeRequestBody annotation = parameter.getParameter().getAnnotation(DecodeRequestBody.class);
        String[] fields = annotation.value();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(body, Map.class);
        System.out.println(map);

        for (String field : fields) {
            String val = map.get(field);
            val = decoder.decrypt(val);
            map.put(field, val);
        }

        body = mapper.writeValueAsString(map).getBytes();
        // 使用解密后的数据，构造新的读取流
        InputStream rawInputStream = new ByteArrayInputStream(body);
        return new HttpInputMessage() {
            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                return rawInputStream;
            }
        };
    }
}
