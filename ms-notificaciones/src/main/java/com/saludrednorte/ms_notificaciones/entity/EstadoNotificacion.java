package com.saludrednorte.ms_notificaciones.entity;

/**
 * Enum que define los posibles estados de una notificación
 * en el ciclo de vida del envío
 */
public enum EstadoNotificacion {
	PENDIENTE("Notificación pendiente de envío"),
	ENVIADA("Notificación enviada exitosamente"),
	FALLIDA("Fallo en el envío de notificación"),
	REINTENTANDO("Reintentando envío de notificación");

	private final String descripcion;

	EstadoNotificacion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}

