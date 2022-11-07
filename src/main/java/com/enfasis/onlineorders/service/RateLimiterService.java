package com.enfasis.onlineorders.service;

import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.model.UserRequestPerIp;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RateLimiterService {
    private CacheManager cacheManager;

    @Cacheable(value = Strings.CACHE_API_HIT_COUNT, key = "{#userIp}")
    public UserRequestPerIp getApiHitCount(String userIp) {
        return UserRequestPerIp.builder().countRequest("0").userIp(userIp).dateRequest(Instant.now()).build();
    }

    public void incrementApiHitCount(String userIp) {
        var obj = cacheManager.getCache(Strings.CACHE_API_HIT_COUNT);
        if(Objects.nonNull(obj)){
            var objCached =  obj.get(userIp);
            if(Objects.nonNull(objCached)){
                var userRequestPerIp = Optional.ofNullable((UserRequestPerIp) objCached.get()).orElse(getApiHitCount(userIp));
                userRequestPerIp.setCountRequest(String.valueOf(Integer.parseInt(userRequestPerIp.getCountRequest()) + 1));
                userRequestPerIp.setDateRequest(Instant.now());
                obj.put(userIp, userRequestPerIp);
            }
        }
    }


}
