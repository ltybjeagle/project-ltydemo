package com.liuty.maven.config;

import com.sun.org.apache.bcel.internal.generic.NEW;
import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {


    /**
     * 启用默认的feign原生契约，可以使用feign的自带注解
     * @return 默认的feign契约
     */
    /*
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

    @Bean
    public Decoder feignDecoder() {
        return new Decoder.Default();
    }

    @Bean
    public Encoder feignEncoder() {
        return new Encoder.Default();
    }*/
}
