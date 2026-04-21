package com.saludrednorte.ms_notificaciones.controller;

import com.saludrednorte.ms_notificaciones.dto.NotificationMapper;
import com.saludrednorte.ms_notificaciones.dto.NotificationRequestDTO;
import com.saludrednorte.ms_notificaciones.dto.NotificationResponseDTO;
import com.saludrednorte.ms_notificaciones.entity.Notification;
import com.saludrednorte.ms_notificaciones.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;
    private final NotificationMapper mapper;

    public NotificationController(NotificationService service, NotificationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@Valid @RequestBody NotificationRequestDTO request) {
        Notification saved = service.create(mapper.requestDtoToEntity(request));
        return ResponseEntity.ok(mapper.entityToResponseDto(saved));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<NotificationResponseDTO>> pending() {
        List<NotificationResponseDTO> result = service.findPending().stream()
                .map(mapper::entityToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(mapper::entityToResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<NotificationResponseDTO>> getByPacienteId(@PathVariable Long pacienteId) {
        List<NotificationResponseDTO> result = service.findByPacienteId(pacienteId).stream()
                .map(mapper::entityToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Void> sendOne(@PathVariable Long id) {
        return service.sendById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/send-by-channel")
    public ResponseEntity<Void> sendByChannel(@PathVariable Long id, @RequestParam("canal") String canal) {
        return service.sendById(id, canal) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/send-all")
    public ResponseEntity<Void> sendAll() {
        service.sendPending();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAll() {
        List<NotificationResponseDTO> result = service.findAll().stream()
                .map(mapper::entityToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
