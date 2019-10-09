package com.seckin.ehcachedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

  private final CustomerRepository customerRepository;

  public AppRunner(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public void run(String... args) {
    logger.info("Customer Fetching Started");
    simulateWaitBetweenCustomerFetch(); //wait 5 seconds. Cache expires every 10 second.
    logger.info("Customer 1 -->" + customerRepository.getById(1L));
    logger.info("Customer 2 -->" + customerRepository.getById(2L));
    logger.info("Customer 3 -->" + customerRepository.getById(3L));

    simulateWaitBetweenCustomerFetch(); //wait 5 seconds. Cache expires every 10 second.
    logger.info("Customer 1 -->" + customerRepository.getById(1L));
    logger.info("Customer 2 -->" + customerRepository.getById(2L));
    logger.info("Customer 3 -->" + customerRepository.getById(3L));

    //Cache Supposed to be expired after this point.
    simulateWaitBetweenCustomerFetch();//wait 5 seconds. Cache expires every 10 second.
    logger.info("Customer 1 -->" + customerRepository.getById(1L));

    simulateWaitBetweenCustomerFetch();//wait 5 seconds. Cache expires every 10 second.
    logger.info("Customer 1 -->" + customerRepository.getById(1L));

    //Cache Supposed to be expired after this point.
    simulateWaitBetweenCustomerFetch();//wait 5 seconds. Cache expires every 10 second.
    logger.info("Customer 1 -->" + customerRepository.getById(1L));

    simulateWaitBetweenCustomerFetch();//wait 5 seconds. Cache expires every 10 second.
    logger.info("Customer 1 -->" + customerRepository.getById(1L));
  }

  private void simulateWaitBetweenCustomerFetch() {
    try {
      long time = 5000L;
      Thread.sleep(time);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }
}
