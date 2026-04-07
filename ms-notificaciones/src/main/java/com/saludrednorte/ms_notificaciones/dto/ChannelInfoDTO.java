package com.saludrednorte.ms_notificaciones.dto;

/**
 * DTO para información de canal de notificación disponible
 */
public class ChannelInfoDTO {

    private String name;
    private String description;

    public ChannelInfoDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

