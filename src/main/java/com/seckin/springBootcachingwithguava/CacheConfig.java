package com.seckin.springBootcachingwithguava;

import com.google.common.cache.CacheBuilder;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

  private final Logger log = LoggerFactory.getLogger(CacheConfig.class);
  private final String CACHE_NAME = "customers";
  private final Long EXPIRE_INTERVAL = 10l;

  @Bean
  @Override
  public CacheManager cacheManager() {
    log.info("Initializing simple Guava Cache Manager.");
    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

    GuavaCache books = new GuavaCache(CACHE_NAME, CacheBuilder.newBuilder()
        .expireAfterWrite(EXPIRE_INTERVAL, TimeUnit.SECONDS)
        .build());

    simpleCacheManager.setCaches(Collections.singletonList(books));

    return simpleCacheManager;
  }

  @Override
  public CacheResolver cacheResolver() {
    return null;
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return new SimpleKeyGenerator();
  }

  @Override
  public CacheErrorHandler errorHandler() {
    return null;
  }
}
