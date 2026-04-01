package com.saludrednorte.ms_listas_espera.controller;

import com.saludrednorte.ms_listas_espera.entity.Estado;
import com.saludrednorte.ms_listas_espera.entity.Gravedad;
import com.saludrednorte.ms_listas_espera.entity.ListaEspera;
import com.saludrednorte.ms_listas_espera.service.ListaEsperaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lista-espera")
public class ListaEsperaController {

    @Autowired
    private ListaEsperaService listaEsperaService;

    @PostMapping
    public ListaEspera agregarAListaEspera(@RequestBody ListaEspera listaEspera) {
        return listaEsperaService.agregarAListaEspera(listaEspera);
    }

    @GetMapping
    public List<ListaEspera> obtenerListaEspera() {
        return listaEsperaService.obtenerListaEspera();
    }

    @GetMapping("/estado/{estado}")
    public List<ListaEspera> obtenerPorEstado(@PathVariable Estado estado) {
        return listaEsperaService.obtenerPorEstado(estado);
    }

    @GetMapping("/gravedad/{gravedad}")
    public List<ListaEspera> obtenerPorGravedad(@PathVariable Gravedad gravedad) {
        return listaEsperaService.obtenerPorGravedad(gravedad);
    }

    @GetMapping("/{id}")
    public Optional<ListaEspera> obtenerPorId(@PathVariable Long id) {
        return listaEsperaService.obtenerPorId(id);
    }

    @PutMapping("/{id}/estado/{estado}")
    public ListaEspera actualizarEstado(@PathVariable Long id, @PathVariable Estado estado) {
        return listaEsperaService.actualizarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    public void eliminarDeListaEspera(@PathVariable Long id) {
        listaEsperaService.eliminarDeListaEspera(id);
    }
}
