# springBoot-caching-with-guava

The purpose of this repository is to demonstrate a very simple way of caching. A repository method is cached and invalidation of cache is done by setting a TTL.

A few notes about Guava caching worth reading.
    
    Generally, the Guava caching utilities are applicable whenever:
        You are willing to spend some memory to improve speed.
        You expect that keys will sometimes get queried more than once.
        Your cache will not need to store more data than what would fit in RAM. (Guava caches are local to a single run of your application. They do not store data in files, or on outside servers. If this does not fit your needs, consider a tool like Memcached.)

Here is the cache manager to create cache and set TTL for the cache entries.

```java
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
```

Here is the method cached: 

```java
  @Cacheable("customers")
  public Customer getById(Long id) {
    logger.info("Fetching Customer from repository.. Id: {}", id);
    return new Customer(id, "Name-" + id, "Surname-" + id);
  }
```

Here are the dependencies for using Guava cache: 
```groovy
    implementation 'org.springframework.boot:spring-boot-starter-cache'
    implementation group: 'com.google.guava', name: 'guava', version: '28.1-jre'
    implementation group: 'org.springframework', name: 'spring-context-support', version: '4.0.0.RELEASE'
```


For more detailed information please read the following documentations: 

    https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache

    https://github.com/google/guava/wiki/CachesExplained