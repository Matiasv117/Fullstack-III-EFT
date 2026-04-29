package com.saludrednorte.ms_optimizacion.dto;

public class NotificationRequestDTO {

    private Long pacienteId;
    private String tipo;
    private String mensaje;

    public NotificationRequestDTO() {
    }

    public NotificationRequestDTO(Long pacienteId, String tipo, String mensaje) {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

