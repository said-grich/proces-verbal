package com.procesverbal.procesverbal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ProcesVerbalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcesVerbalApplication.class, args);
    }

}
