package com.saludrednorte.ms_notificaciones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manejador global de excepciones
 * Proporciona respuestas consistentes para todos los errores
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${spring.application.name:ms-notificaciones}")
    private String serviceName;

    /**
     * Maneja errores de validación de DTOs
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, ServletWebRequest request) {
        logger.warn("Error de validación", ex);
        return buildError(HttpStatus.BAD_REQUEST, "Solicitud invalida", ex.getClass().getSimpleName(), request.getRequest().getRequestURI());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex, ServletWebRequest request) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value()) != null ?
            HttpStatus.resolve(ex.getStatusCode().value()) : HttpStatus.INTERNAL_SERVER_ERROR;
        String reason = ex.getReason() == null ? status.toString() : ex.getReason();
        logger.warn("Error controlado: {}", reason);
        return buildError(status, reason, ex.getClass().getSimpleName(), request.getRequest().getRequestURI());
    }

    /**
     * Maneja excepciones generales no capturadas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, ServletWebRequest request) {
        logger.error("Error no controlado: ", ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", ex.getClass().getSimpleName(), request.getRequest().getRequestURI());
    }

    /**
     * Maneja excepciones de recurso no encontrado
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, ServletWebRequest request) {
        logger.warn("Error de argumento inválido: {}", ex.getMessage());
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), request.getRequest().getRequestURI());
    }

    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message, String error, String path) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("timestamp", LocalDateTime.now().toString());
        payload.put("status", status.value());
        payload.put("error", error);
        payload.put("message", message);
        payload.put("path", path);
        payload.put("service", serviceName);
        return ResponseEntity.status(status).body(payload);
    }
}

