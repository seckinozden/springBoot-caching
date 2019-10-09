This Project provides sample implementation for Caching with SpringBoot and EhCache 2.x

When SpringBoot recognizes following dependencies on classpath it sets up the Caching mechanism automatly. But to enable caching you need use @EnableCaching annotation in your application. Either you can Create a new Configuration or you can put @EnableCaching annotation on top of your main class or one of your existing configuration class. This project creates a new configuration for enabling caching.


```java
@Configuration
@EnableCaching
public class CacheConfig {
}
```

When EhCache dependencies are recognized, spring boot will detect the `ehcache.xml` file and configure the cache with this file.

```xml
<ehcache>
    <diskStore path="java.io.tmpdir"/>

    <cache name="customers"
           maxElementsInMemory="2"
           overflowToDisk="true"
           eternal="false"
           timeToLiveSeconds="10"/>
    <!-- eternal attribute - if true indicates that mappings never expire -->
    <!-- overflowToDisk - Customers needs to be Serializable to be able to overflowToDisk -->
    <!-- timeToLiveSeconds="27000" - Expire in 45 minutes -->
</ehcache>
```



Here is the sample method for demonstating the usage of @Cacheable. 
Cache name on @Cacheable annotation should be the same name defined in `ehcache.xml`

```java
  //Do not cache when Customer Id equals 2
  @Cacheable(value="customers", unless="#result.id==2")
  public Customer getById(Long id) {
    logger.info("Fetching Customer from repository.. Id: {}", id);
    return new Customer(id, "Name-" + id, "Surname-" + id);
  }
```