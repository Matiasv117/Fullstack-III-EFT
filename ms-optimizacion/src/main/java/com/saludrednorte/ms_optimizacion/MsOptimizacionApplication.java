package com.saludrednorte.ms_optimizacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsOptimizacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsOptimizacionApplication.class, args);
    }

}
