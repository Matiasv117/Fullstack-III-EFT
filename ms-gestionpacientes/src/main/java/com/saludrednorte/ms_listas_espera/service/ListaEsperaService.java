package com.saludrednorte.ms_listas_espera.service;

import com.saludrednorte.ms_listas_espera.client.NotificationClient;
import com.saludrednorte.ms_listas_espera.dto.NotificationRequestDTO;
import com.saludrednorte.ms_listas_espera.dto.TipoNotificacion;
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

    @Autowired
    private NotificationClient notificationClient;

    public ListaEspera agregarAListaEspera(ListaEspera listaEspera) {
        listaEspera.setEstado(Estado.PENDIENTE);
        ListaEspera listaEsperaGuardada = listaEsperaRepository.save(listaEspera);
        enviarNotificacion(listaEsperaGuardada, TipoNotificacion.PACIENTE_ASIGNADO);
        return listaEsperaGuardada;
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
            listaEsperaRepository.save(listaEspera);
            enviarNotificacion(listaEspera, TipoNotificacion.ACTUALIZACION_ESTADO);
            return listaEspera;
        }
        return null;
    }

    public void eliminarDeListaEspera(Long id) {
        Optional<ListaEspera> optional = listaEsperaRepository.findById(id);
        if (optional.isPresent()) {
            ListaEspera listaEspera = optional.get();
            listaEsperaRepository.deleteById(id);
            enviarNotificacion(listaEspera, TipoNotificacion.ELIMINACION_LISTA_ESPERA);
        }
    }

    private void enviarNotificacion(ListaEspera listaEspera, TipoNotificacion tipo) {
        NotificationRequestDTO requestDTO = new NotificationRequestDTO();
        requestDTO.setPacienteId(listaEspera.getPaciente().getId());
        requestDTO.setTipo(tipo);
        requestDTO.setMensaje("Actualización en la lista de espera: " + listaEspera.getId());
        notificationClient.createNotification(requestDTO);
    }
}
