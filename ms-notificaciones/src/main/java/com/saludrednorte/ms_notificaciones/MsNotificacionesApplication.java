package com.saludrednorte.ms_notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicación principal del Microservicio de Notificaciones
 * Responsable de gestionar el envío de notificaciones a pacientes
 * en el sistema de salud RedNorte
 */
@SpringBootApplication
@EnableScheduling
public class MsNotificacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsNotificacionesApplication.class, args);
    }
}

