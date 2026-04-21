package com.saludrednorte.ms_listas_espera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO para request de creación de notificaciones.
 */
public class NotificationRequestDTO {

    @NotNull
    @Positive
    private Long pacienteId;

    @NotNull
    private TipoNotificacion tipo;

    @NotBlank
    @Size(min = 5, max = 2000)
    private String mensaje;

    public NotificationRequestDTO() {
    }

    public NotificationRequestDTO(Long pacienteId, TipoNotificacion tipo, String mensaje) {
        this.pacienteId = pacienteId;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
