package com.heditra.events.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadLetterQueueHandler {
    
    @KafkaListener(topics = "${kafka.dlq.topic:event-dlq}", groupId = "${spring.kafka.consumer.group-id:default-group}-dlq")
    public void handleDeadLetter(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(value = KafkaHeaders.EXCEPTION_MESSAGE, required = false) String exceptionMessage,
            Acknowledgment acknowledgment) {
        
        log.error("Dead letter queue received message from topic: {}, exception: {}, message: {}",
                topic, exceptionMessage, message);
        
        // Store in database or send alert
        // For now, just log and acknowledge
        
        if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
