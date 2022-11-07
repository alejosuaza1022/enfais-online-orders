package com.enfasis.onlineorders;

import com.enfasis.onlineorders.constants.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableCaching
public class OnlineOrders {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig();
        defaultCacheConfig.disableCachingNullValues();
        Map<String, RedisCacheConfiguration> cacheConfigurations =
                new HashMap<>();
        cacheConfigurations.put(
                Strings.CACHE_API_HIT_COUNT, defaultCacheConfig.
                        entryTtl(Duration.ofSeconds(30))
        );
        cacheConfigurations.put(
                Strings.CACHE_USER, defaultCacheConfig.
                        entryTtl(Duration.ofSeconds(30))
        );
        return RedisCacheManager.builder(connectionFactory).
                cacheDefaults(defaultCacheConfig).
                withInitialCacheConfigurations(cacheConfigurations).
                build();
    }


    public static void main(String[] args) {
        SpringApplication.run(OnlineOrders.class, args);
    }

}
