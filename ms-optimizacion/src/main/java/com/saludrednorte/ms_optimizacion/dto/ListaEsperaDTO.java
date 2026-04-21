package com.saludrednorte.ms_optimizacion.dto;

public class ListaEsperaDTO {

    private Long id;
    private Long pacienteId;
    private String interconsulta;
    private String gravedad;
    private String estado;

    public ListaEsperaDTO() {
    }

    public ListaEsperaDTO(Long id, Long pacienteId, String interconsulta, String gravedad, String estado) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.interconsulta = interconsulta;
        this.gravedad = gravedad;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getInterconsulta() {
        return interconsulta;
    }

    public void setInterconsulta(String interconsulta) {
        this.interconsulta = interconsulta;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
