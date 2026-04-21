package com.saludrednorte.ms_optimizacion.client;

import com.saludrednorte.ms_optimizacion.dto.ListaEsperaDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-listas-espera")
@CircuitBreaker(name = "listaEsperaService")
public interface ListaEsperaClient {

    @GetMapping("/lista-espera")
    List<ListaEsperaDTO> getListaEspera();
}
