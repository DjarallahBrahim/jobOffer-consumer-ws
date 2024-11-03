package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.ws.core.JobOfferCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topic.kafka.name}")
public class JobOfferCreatedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @KafkaHandler
    public void handler(JobOfferCreatedEvent jobOfferCreatedEvent){
        LOGGER.info("[JobOfferCreatedEventHandler] received a new event: {} ", jobOfferCreatedEvent.getTitle());
    }
}
