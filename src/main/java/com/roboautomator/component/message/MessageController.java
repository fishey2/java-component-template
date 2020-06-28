package com.roboautomator.component.message;

import static com.roboautomator.component.util.StringHelper.cleanString;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageRepository messageRepository;
    private final ActiveMQProducer producer;

    @PostMapping
    public ResponseEntity<String> sendMessageToQueue(@RequestBody UserMessage userMessage) {

        var message = cleanString(userMessage.getMessageBody());

        log.info("Received request to send the message \"{}\" to queue", message);

        try {
            producer.sendMessage(message);
        } catch (JmsException e) {
            log.error("Could not process the message \"{}\", returning", message);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.toString());
        }

        return ResponseEntity.ok(message);
    }

    @GetMapping(path = "/{correlationId}")
    public ResponseEntity<Message> getMessageFromDatabase(@PathVariable UUID correlationId) {
        var message = messageRepository.findAllByCorrelationId(correlationId);

        return message
            .map(entity -> ResponseEntity.ok(convertToMessage(entity)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Message convertToMessage(MessageEntity entity) {
        return Message.builder()
            .correlationId(entity.getCorrelationId())
            .messageBody(entity.getMessage())
            .build();
    }
}
