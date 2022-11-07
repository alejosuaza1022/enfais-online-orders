package com.enfasis.onlineorders.service;

import com.enfasis.onlineorders.constants.Strings;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RateLimiterService {
    private CacheManager cacheManager;

    @Cacheable(value = Strings.CACHE_API_HIT_COUNT, key = "{#userIp}")
    public Integer getApiHitCount(String userIp) {
        return 0;
    }

    public void incrementApiHitCount(String userIp) {
        var obj = cacheManager.getCache(Strings.CACHE_API_HIT_COUNT);
        if (Objects.nonNull(obj)) {
            var objCached = obj.get(userIp);
            if (Objects.nonNull(objCached)) {
                var userRequestPerIp = Optional.ofNullable((Integer) objCached.get()).orElse(0);
                obj.put(userIp, userRequestPerIp + 1);
            }
        }
    }


}
