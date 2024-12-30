package com.xander.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
// @EnableTransactionManagement // This annotation is moved to /config/TransactionConfig.java
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*
	 * This snippet is moved to /config/TransactionConfig.java
	 *
	 *@Bean
	 *public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
	 *	return new MongoTransactionManager(dbFactory);
	 *}
	 * 
	 */

}
