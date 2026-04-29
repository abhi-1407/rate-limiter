package com.abhilash.ratelimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaScriptConfig {

    @Bean
    public RedisScript<Long> slidingWindowScript() {

        DefaultRedisScript<Long> script =
                new DefaultRedisScript<>();

        script.setScriptSource(
                new ResourceScriptSource(
                        new ClassPathResource(
                                "scripts/sliding-window.lua"
                        )
                )
        );

        script.setResultType(Long.class);

        return script;
    }
}