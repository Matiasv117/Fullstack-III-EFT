package com.saludrednorte.ms_notificaciones.service.strategy;

import com.saludrednorte.ms_notificaciones.entity.Notification;

/**
 * Interfaz Strategy para diferentes canales de notificación.
 */
public interface NotificationStrategy {

	void send(Notification notification) throws Exception;

	String getChannelName();
}

