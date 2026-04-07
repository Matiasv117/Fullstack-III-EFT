package com.saludrednorte.ms_notificaciones.dto;

import com.saludrednorte.ms_notificaciones.entity.EstadoNotificacion;
import com.saludrednorte.ms_notificaciones.entity.TipoNotificacion;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de notificaciones.
 * Contiene los datos expuestos por la API REST.
 */
public class NotificationResponseDTO {

	private Long id;
	private Long pacienteId;
	private TipoNotificacion tipo;
	private String mensaje;
	private EstadoNotificacion estado;
	private LocalDateTime creadoAt;
	private LocalDateTime enviadoAt;
	private Integer intentosEnvio;

	public NotificationResponseDTO() {
	}

	public NotificationResponseDTO(Long id, Long pacienteId, TipoNotificacion tipo, String mensaje,
								   EstadoNotificacion estado, LocalDateTime creadoAt,
								   LocalDateTime enviadoAt, Integer intentosEnvio) {
		this.id = id;
		this.pacienteId = pacienteId;
		this.tipo = tipo;
		this.mensaje = mensaje;
		this.estado = estado;
		this.creadoAt = creadoAt;
		this.enviadoAt = enviadoAt;
		this.intentosEnvio = intentosEnvio;
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

	public EstadoNotificacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoNotificacion estado) {
		this.estado = estado;
	}

	public LocalDateTime getCreadoAt() {
		return creadoAt;
	}

	public void setCreadoAt(LocalDateTime creadoAt) {
		this.creadoAt = creadoAt;
	}

	public LocalDateTime getEnviadoAt() {
		return enviadoAt;
	}

	public void setEnviadoAt(LocalDateTime enviadoAt) {
		this.enviadoAt = enviadoAt;
	}

	public Integer getIntentosEnvio() {
		return intentosEnvio;
	}

	public void setIntentosEnvio(Integer intentosEnvio) {
		this.intentosEnvio = intentosEnvio;
	}
}

