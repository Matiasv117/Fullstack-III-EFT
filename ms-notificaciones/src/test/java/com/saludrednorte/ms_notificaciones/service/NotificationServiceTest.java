package com.saludrednorte.ms_notificaciones.service;

import com.saludrednorte.ms_notificaciones.entity.EstadoNotificacion;
import com.saludrednorte.ms_notificaciones.entity.Notification;
import com.saludrednorte.ms_notificaciones.entity.TipoNotificacion;
import com.saludrednorte.ms_notificaciones.repository.NotificationRepository;
import com.saludrednorte.ms_notificaciones.service.factory.NotificationStrategyFactory;
import com.saludrednorte.ms_notificaciones.service.strategy.NotificationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests unitarios para NotificationService
 * Valida la lógica de creación, envío y gestión de notificaciones
 */
class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private NotificationStrategyFactory strategyFactory;

    @Mock
    private NotificationStrategy strategy;

    @InjectMocks
    private NotificationService service;

    private Notification notification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notification = new Notification();
        notification.setId(1L);
        notification.setPacienteId(123L);
        notification.setTipo(TipoNotificacion.CITA_CONFIRMADA);
        notification.setMensaje("Su cita ha sido confirmada");
        notification.setEstado(EstadoNotificacion.PENDIENTE);
        notification.setIntentosEnvio(0);
    }

    @Test
    void testCreateNotification() {
        when(repository.save(any(Notification.class))).thenReturn(notification);

        Notification result = service.create(notification);

        assertThat(result).isNotNull();
        assertThat(result.getEstado()).isEqualTo(EstadoNotificacion.PENDIENTE);
        verify(repository, times(1)).save(notification);
    }

    @Test
    void testFindPending() {
        when(repository.findByEstado(EstadoNotificacion.PENDIENTE)).thenReturn(List.of(notification));

        List<Notification> result = service.findPending();

        assertThat(result).hasSize(1);
        verify(repository, times(1)).findByEstado(EstadoNotificacion.PENDIENTE);
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(notification));

        Optional<Notification> result = service.findById(1L);

        assertThat(result).isPresent();
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSendById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(notification));
        when(strategyFactory.getStrategy("EMAIL")).thenReturn(Optional.of(strategy));
        when(repository.save(any(Notification.class))).thenReturn(notification);
        doNothing().when(strategy).send(notification);

        boolean result = service.sendById(1L);

        assertThat(result).isTrue();
        verify(repository, times(1)).save(any(Notification.class));
    }

    @Test
    void testSendByIdNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        boolean result = service.sendById(999L);

        assertThat(result).isFalse();
        verify(repository, times(1)).findById(999L);
    }
}
