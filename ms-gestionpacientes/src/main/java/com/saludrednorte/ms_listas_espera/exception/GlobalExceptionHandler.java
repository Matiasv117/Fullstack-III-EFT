package com.saludrednorte.ms_listas_espera.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.application.name:ms-listas-espera}")
    private String serviceName;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, ServletWebRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Solicitud invalida", ex.getMessage(), request.getRequest().getRequestURI());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex, ServletWebRequest request) {
        String reason = ex.getReason() == null ? ex.getStatusCode().toString() : ex.getReason();
        return buildError(HttpStatus.valueOf(ex.getStatusCode().value()), reason, ex.getMessage(), request.getRequest().getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex, ServletWebRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), request.getRequest().getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex, ServletWebRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", ex.getClass().getSimpleName(), request.getRequest().getRequestURI());
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


