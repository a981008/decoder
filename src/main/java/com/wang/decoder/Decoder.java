package com.wang.decoder;

/**
 * 解码器
 *
 * @author Wang
 * @since 2022/7/27
 */
public interface Decoder {
    /**
     * 密文解密为明文
     */
    String decrypt(String encryptStr);
}
