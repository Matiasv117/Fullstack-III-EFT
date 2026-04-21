package com.saludrednorte.ms_listas_espera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsListasEsperaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsListasEsperaApplication.class, args);
	}

}
