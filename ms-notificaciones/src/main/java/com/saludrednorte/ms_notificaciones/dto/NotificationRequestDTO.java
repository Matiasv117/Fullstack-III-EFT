package com.saludrednorte.ms_notificaciones.dto;

import com.saludrednorte.ms_notificaciones.entity.TipoNotificacion;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * DTO para request de creación de notificaciones.
 * Valida los datos de entrada desde el cliente.
 */
public class NotificationRequestDTO {

    @NotNull(message = "El ID del paciente es obligatorio")
    @Positive(message = "El ID del paciente debe ser mayor a 0")
    private Long pacienteId;

    @NotNull(message = "El tipo de notificación es obligatorio")
    private TipoNotificacion tipo;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 5, max = 2000, message = "El mensaje debe tener entre 5 y 2000 caracteres")
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

