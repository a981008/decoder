package com.wang.decoder.impl;

import com.wang.decoder.Decoder;
import com.wang.decoder.util.AesEncryptUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * 采用 Aes 的默认解码器
 *
 * @author Wang
 * @since 2022/7/27
 */

public class DefaultDecoder implements Decoder {
    @Override
    public String decrypt(String encryptStr) {
        return AesEncryptUtils.decrypt(encryptStr);
    }
}
