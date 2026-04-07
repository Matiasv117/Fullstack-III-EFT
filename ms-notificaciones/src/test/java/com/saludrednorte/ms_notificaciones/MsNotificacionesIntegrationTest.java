package com.saludrednorte.ms_notificaciones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saludrednorte.ms_notificaciones.dto.NotificationRequestDTO;
import com.saludrednorte.ms_notificaciones.entity.TipoNotificacion;
import com.saludrednorte.ms_notificaciones.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de integración para el microservicio de notificaciones
 * Prueba el flujo completo de la aplicación
 */
@DisplayName("Tests de Integración - Microservicio de Notificaciones")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MsNotificacionesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationRepository repository;

    @Test
    @DisplayName("Flujo completo: Crear y enviar notificación")
    void testCompleteNotificationFlow() throws Exception {
        // Limpiar base de datos
        repository.deleteAll();

        // 1. Crear notificación
        NotificationRequestDTO requestDTO = new NotificationRequestDTO();
        requestDTO.setPacienteId(123L);
        requestDTO.setTipo(TipoNotificacion.CITA_CONFIRMADA);
        requestDTO.setMensaje("Su cita ha sido confirmada para mañana a las 10:00");

        mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        // 2. Enviar notificación
        mockMvc.perform(post("/api/notifications/1/send"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Health check del microservicio")
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/notifications/info/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("operacional")));
    }
}
