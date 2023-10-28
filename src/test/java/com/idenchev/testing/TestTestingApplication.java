package com.idenchev.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTestingApplication {

    public static void main(String[] args) {
        SpringApplication.from(TestingApplication::main).with(TestTestingApplication.class).run(args);
    }

}
