package com.seckin.ehcachedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository {

  private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

  //Do not cache when returned Customer's id equals 2
  @Cacheable(value="customers", unless="#result.id==2")
  public Customer getById(Long id) {
    logger.info("Fetching Customer from repository.. Id: {}", id);
    return new Customer(id, "Name-" + id, "Surname-" + id);
  }
}
