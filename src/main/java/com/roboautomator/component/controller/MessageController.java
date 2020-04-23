package com.roboautomator.component.controller;

import com.roboautomator.component.service.message.activemq.producer.ActiveMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.*;

import static com.roboautomator.component.util.StringHelper.cleanString;

@RestController
@RequestMapping("/message")
public class MessageController {

    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    private final ActiveMQProducer producer;

    public MessageController(ActiveMQProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> sendMessageToQueue(@RequestBody String message) {

        message = cleanString(message);

        log.info("Received request to send the message \"{}\" to queue", message);

        try {
            producer.sendMessage(message);
        } catch(JmsException e) {
            log.error("Could not process the message \"{}\", returning", message);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.toString());
        }

        return ResponseEntity.ok(message);
    }
}
