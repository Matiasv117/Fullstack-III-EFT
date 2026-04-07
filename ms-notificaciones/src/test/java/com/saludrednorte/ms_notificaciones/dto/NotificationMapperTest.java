package com.saludrednorte.ms_notificaciones.dto;

import com.saludrednorte.ms_notificaciones.entity.EstadoNotificacion;
import com.saludrednorte.ms_notificaciones.entity.Notification;
import com.saludrednorte.ms_notificaciones.entity.TipoNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationMapperTest {

    private NotificationMapper mapper;
    private NotificationRequestDTO requestDTO;
    private Notification notification;

    @BeforeEach
    void setUp() {
        mapper = new NotificationMapper();
        requestDTO = new NotificationRequestDTO(123L, TipoNotificacion.CITA_CONFIRMADA, "Su cita ha sido confirmada");

        notification = new Notification();
        notification.setId(1L);
        notification.setPacienteId(123L);
        notification.setTipo(TipoNotificacion.CITA_CONFIRMADA);
        notification.setMensaje("Su cita ha sido confirmada");
        notification.setEstado(EstadoNotificacion.PENDIENTE);
        notification.setCreadoAt(LocalDateTime.now());
        notification.setIntentosEnvio(0);
    }

    @Test
    void testRequestDtoToEntity() {
        Notification result = mapper.requestDtoToEntity(requestDTO);

        assertThat(result.getPacienteId()).isEqualTo(123L);
        assertThat(result.getTipo()).isEqualTo(TipoNotificacion.CITA_CONFIRMADA);
        assertThat(result.getMensaje()).isEqualTo("Su cita ha sido confirmada");
    }

    @Test
    void testEntityToResponseDto() {
        NotificationResponseDTO result = mapper.entityToResponseDto(notification);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEstado()).isEqualTo(EstadoNotificacion.PENDIENTE);
        assertThat(result.getIntentosEnvio()).isEqualTo(0);
    }
}
