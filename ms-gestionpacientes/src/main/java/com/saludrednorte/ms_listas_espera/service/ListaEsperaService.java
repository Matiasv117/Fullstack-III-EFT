package com.saludrednorte.ms_listas_espera.service;

import com.saludrednorte.ms_listas_espera.client.NotificationClient;
import com.saludrednorte.ms_listas_espera.dto.NotificationRequestDTO;
import com.saludrednorte.ms_listas_espera.entity.Estado;
import com.saludrednorte.ms_listas_espera.entity.Gravedad;
import com.saludrednorte.ms_listas_espera.entity.ListaEspera;
import com.saludrednorte.ms_listas_espera.repository.ListaEsperaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ListaEsperaService {

    private static final Logger logger = LoggerFactory.getLogger(ListaEsperaService.class);

    @Autowired
    private ListaEsperaRepository listaEsperaRepository;

    @Autowired
    private NotificationClient notificationClient;

    public ListaEspera agregarAListaEspera(ListaEspera listaEspera) {
        if (listaEspera.getPaciente() == null || listaEspera.getPaciente().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe informar el paciente asociado");
        }
        listaEspera.setEstado(Estado.PENDIENTE);
        ListaEspera listaEsperaGuardada = listaEsperaRepository.save(listaEspera);
        enviarNotificacion(listaEsperaGuardada, "PACIENTE_ASIGNADO");
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
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de lista de espera no encontrado");
        }

        ListaEspera listaEspera = optional.get();
        listaEspera.setEstado(estado);
        listaEsperaRepository.save(listaEspera);
        enviarNotificacion(listaEspera, "ACTUALIZACION_ESTADO");
        return listaEspera;
    }

    public void eliminarDeListaEspera(Long id) {
        Optional<ListaEspera> optional = listaEsperaRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de lista de espera no encontrado");
        }

        ListaEspera listaEspera = optional.get();
        listaEsperaRepository.deleteById(id);
        enviarNotificacion(listaEspera, "ELIMINACION_LISTA_ESPERA");
    }

    private void enviarNotificacion(ListaEspera listaEspera, String tipo) {
        NotificationRequestDTO requestDTO = new NotificationRequestDTO();
        requestDTO.setPacienteId(listaEspera.getPaciente().getId());
        requestDTO.setTipo(tipo);
        requestDTO.setMensaje("Actualización en la lista de espera: " + listaEspera.getId());
        try {
            notificationClient.createNotification(requestDTO);
        } catch (Exception ex) {
            logger.warn("No se pudo registrar la notificación para listaEsperaId={} tipo={}", listaEspera.getId(), tipo, ex);
        }
    }
}
