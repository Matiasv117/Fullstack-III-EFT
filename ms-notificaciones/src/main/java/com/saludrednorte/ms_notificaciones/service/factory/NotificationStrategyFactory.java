package com.saludrednorte.ms_notificaciones.service.factory;

import com.saludrednorte.ms_notificaciones.service.strategy.NotificationStrategy;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Factory Pattern para crear y seleccionar estrategias de notificación
 * Permite agregar nuevos canales sin modificar el código existente
 * Principio Open/Closed: abierto para extensión, cerrado para modificación
 */
@Component
public class NotificationStrategyFactory {

    private static final Logger logger = LoggerFactory.getLogger(NotificationStrategyFactory.class);

    private final List<NotificationStrategy> strategies;

    public NotificationStrategyFactory(List<NotificationStrategy> strategies) {
        this.strategies = strategies;
        logger.info("Estrategias de notificación disponibles: {}",
                   strategies.stream().map(NotificationStrategy::getChannelName).collect(Collectors.toList()));
    }

    /**
     * Obtiene una estrategia específica por nombre de canal
     * @param channelName Nombre del canal (EMAIL, SMS, PUSH)
     * @return Strategy encontrada
     */
    public Optional<NotificationStrategy> getStrategy(String channelName) {
        return strategies.stream()
                .filter(s -> s.getChannelName().equalsIgnoreCase(channelName))
                .findFirst();
    }

    /**
     * Obtiene todas las estrategias disponibles
     */
    public List<NotificationStrategy> getAllStrategies() {
        return strategies;
    }

    /**
     * Obtiene la estrategia por defecto (Email)
     */
    public NotificationStrategy getDefaultStrategy() {
        return getStrategy("EMAIL")
                .orElseThrow(() -> new IllegalStateException("Estrategia EMAIL no disponible"));
    }
}

