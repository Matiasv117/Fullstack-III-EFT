package com.saludrednorte.ms_notificaciones.entity;

/**
 * Enum que define los tipos de notificaciones que el sistema puede enviar
 * relacionadas con la gestión de citas médicas
 */
public enum TipoNotificacion {
    CITA_CONFIRMADA("Confirmación de cita médica asignada"),
    CITA_CANCELADA("Notificación de cancelación de cita"),
    RECORDATORIO_CITA("Recordatorio de cita próxima"),
    CAMBIO_HORARIO("Cambio de horario de cita"),
    PACIENTE_ASIGNADO("Paciente asignado a lista de espera"),
    CAMBIO_PRIORIDAD("Cambio de prioridad en lista de espera"),
    POSICION_ACTUALIZADA("Actualización de posición en lista de espera");

    private final String descripcion;

    TipoNotificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

