package com.saludrednorte.ms_listas_espera.service;

import com.saludrednorte.ms_listas_espera.entity.Estado;
import com.saludrednorte.ms_listas_espera.entity.Gravedad;
import com.saludrednorte.ms_listas_espera.entity.ListaEspera;
import com.saludrednorte.ms_listas_espera.repository.ListaEsperaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaEsperaService {

    @Autowired
    private ListaEsperaRepository listaEsperaRepository;

    public ListaEspera agregarAListaEspera(ListaEspera listaEspera) {
        listaEspera.setEstado(Estado.PENDIENTE);
        return listaEsperaRepository.save(listaEspera);
    }

    public List<ListaEspera> obtenerListaEspera() {
        return listaEsperaRepository.findAll();
    }

    public List<ListaEspera> obtenerPorEstado(Estado estado) {
        return listaEsperaRepository.findByEstado(estado);
    }

    public List<ListaEspera> obtenerPorGravedad(Gravedad gravedad) {
        return listaEsperaRepository.findByGravedadOrderByIdAsc(gravedad);
    }

    public Optional<ListaEspera> obtenerPorId(Long id) {
        return listaEsperaRepository.findById(id);
    }

    public ListaEspera actualizarEstado(Long id, Estado estado) {
        Optional<ListaEspera> optional = listaEsperaRepository.findById(id);
        if (optional.isPresent()) {
            ListaEspera listaEspera = optional.get();
            listaEspera.setEstado(estado);
            return listaEsperaRepository.save(listaEspera);
        }
        return null;
    }

    public void eliminarDeListaEspera(Long id) {
        listaEsperaRepository.deleteById(id);
    }
}
