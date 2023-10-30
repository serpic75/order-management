package com.aspect.queue.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.aspect.queue.repo"})
@EncryptablePropertySource("classpath:persistence.yml")
@EntityScan(basePackages = { "com.aspect.queue" })
public class PersitenceConfig {
}
