package com.saludrednorte.ms_notificaciones.repository;

import com.saludrednorte.ms_notificaciones.entity.Notification;
import com.saludrednorte.ms_notificaciones.entity.EstadoNotificacion;
import com.saludrednorte.ms_notificaciones.entity.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceso a datos de Notification
 * Implementa el patrón Repository Pattern
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEstado(EstadoNotificacion estado);
    List<Notification> findByPacienteId(Long pacienteId);
    boolean existsByPacienteIdAndTipoAndMensajeAndEstado(Long pacienteId, TipoNotificacion tipo, String mensaje, EstadoNotificacion estado);
}
