package com.wang.decoder.autoconfigure;

import com.wang.decoder.Decoder;
import com.wang.decoder.annotation.DecodeRequestBody;
import com.wang.decoder.impl.DefaultDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 自动装配 decoder
 *
 * @author Wang
 * @since 2022/7/27
 */
@Configuration
@ComponentScan(basePackages = "com.wang.decoder")
@ConditionalOnClass(DecodeRequestBody.class)
public class DecoderAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(Decoder.class)
    public Decoder decoder() {
        return new DefaultDecoder();
    }
}
