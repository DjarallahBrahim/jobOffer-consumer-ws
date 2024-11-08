package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.consumer.ws.emailnotification.entity.ApplyActionHistory;
import com.joboffer.consumer.ws.emailnotification.entity.ProcessedEvent;
import com.joboffer.consumer.ws.emailnotification.exceptions.NotRetryableException;
import com.joboffer.ws.core.JobOfferCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@KafkaListener(topics = "${topic.kafka.name}")
public class JobOfferCreatedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private JobOfferApplyEventService jobOfferApplyService;

    public JobOfferCreatedEventHandler(JobOfferApplyEventService jobOfferApplyService) {
        this.jobOfferApplyService = jobOfferApplyService;
    }

    @KafkaHandler
    @Transactional
    public void handler(@Payload JobOfferCreatedEvent jobOfferCreatedEvent, @Header("messageId") String messageId,
                        @Header(KafkaHeaders.RECEIVED_KEY) String messageKey){

        ProcessedEvent processedEvent = new ProcessedEvent();
        LOGGER.info("[JobOfferCreatedEventHandler] received a new event id: {} ", jobOfferCreatedEvent.getJobOfferId());
        LOGGER.info("[JobOfferCreatedEventHandler] verify if the Kafka event is new ?");
        try {
        if(jobOfferApplyService.isTheEventKafkaWasConsumedAlready(messageId)) {
            processedEvent.setMessageId(jobOfferCreatedEvent.getJobOfferId());
            LOGGER.info("[JobOfferCreatedEventHandler] received a new event: {} ", jobOfferCreatedEvent.getTitle());
            LOGGER.info("[JobOfferCreatedEventHandler] messageId: {}, messageKey: {}", messageId, messageKey);
            jobOfferApplyService.saveJobOfferEventKafka(processedEvent);
            LOGGER.info("[JobOfferCreatedEventHandler] event kafka  saved to bdd: {}, messageKey: {}", messageId, messageKey);

        }else
            LOGGER.info("[JobOfferCreatedEventHandler] event kafka already consumed, messageId : {}", messageId);
        } catch (DataIntegrityViolationException ex) {
            throw new NotRetryableException(ex);
        }

    }


}
