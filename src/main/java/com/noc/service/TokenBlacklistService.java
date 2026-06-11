package com.noc.service;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void blacklistToken(String token) {
        if (token != null) {
            blacklistedTokens.add(token);
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return token != null && blacklistedTokens.contains(token);
    }
}
