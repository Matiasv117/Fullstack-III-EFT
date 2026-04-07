package com.saludrednorte.ms_notificaciones.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

    @Test
    void testDefaultConstructorInitialization() {
        assertThat(notification.getEstado()).isEqualTo(EstadoNotificacion.PENDIENTE);
        assertThat(notification.getCreadoAt()).isNotNull();
        assertThat(notification.getIntentosEnvio()).isEqualTo(0);
    }

    @Test
    void testCanSetMainFields() {
        notification.setId(123L);
        notification.setPacienteId(456L);
        notification.setTipo(TipoNotificacion.CITA_CONFIRMADA);
        notification.setMensaje("Su cita ha sido confirmada");
        notification.setEnviadoAt(LocalDateTime.now());

        assertThat(notification.getId()).isEqualTo(123L);
        assertThat(notification.getPacienteId()).isEqualTo(456L);
        assertThat(notification.getTipo()).isEqualTo(TipoNotificacion.CITA_CONFIRMADA);
        assertThat(notification.getMensaje()).isEqualTo("Su cita ha sido confirmada");
        assertThat(notification.getEnviadoAt()).isNotNull();
    }

    @Test
    void testAllNotificationTypesValid() {
        assertThat(TipoNotificacion.values())
                .contains(
                        TipoNotificacion.CITA_CONFIRMADA,
                        TipoNotificacion.CITA_CANCELADA,
                        TipoNotificacion.RECORDATORIO_CITA,
                        TipoNotificacion.CAMBIO_HORARIO,
                        TipoNotificacion.PACIENTE_ASIGNADO,
                        TipoNotificacion.CAMBIO_PRIORIDAD,
                        TipoNotificacion.POSICION_ACTUALIZADA
                );
    }

    @Test
    void testAllStatesValid() {
        assertThat(EstadoNotificacion.values())
                .contains(
                        EstadoNotificacion.PENDIENTE,
                        EstadoNotificacion.ENVIADA,
                        EstadoNotificacion.FALLIDA,
                        EstadoNotificacion.REINTENTANDO
                );
    }
}
