package com.musicly.store.Services;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistService {
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blacklistTokens(String token){
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token){
        return blacklistedTokens.contains(token);
    }

    @Scheduled(fixedRate=3600000)
    public void cleanUpExpiredTokens(){

    }
    
}
