package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.consumer.ws.emailnotification.exceptions.NotRetryableException;
import com.joboffer.ws.core.JobOfferCreatedEvent;
import com.joboffer.ws.core.jpa.entities.ApplyActionHistory;
import com.joboffer.ws.core.jpa.entities.ProcessedEvent;
import com.joboffer.ws.core.jpa.entities.Candidate;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.mail.MailException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@KafkaListener(topics = "${topic.kafka.name}")
public class JobOfferCreatedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final JobOfferApplyEventService jobOfferApplyService;
    private final EmailService emailService;
    private final CandidateService candidateService;


    public JobOfferCreatedEventHandler(JobOfferApplyEventService jobOfferApplyService, EmailService emailService, CandidateService candidateService) {
        this.jobOfferApplyService = jobOfferApplyService;
        this.emailService = emailService;
        this.candidateService = candidateService;
    }

    @KafkaHandler
    @Transactional
    public void handler(@Payload JobOfferCreatedEvent jobOfferCreatedEvent, @Header("messageId") String messageId,
                        @Header(KafkaHeaders.RECEIVED_KEY) String messageKey){

        ProcessedEvent processedEvent = new ProcessedEvent();
        LOGGER.info("[JobOfferCreatedEventHandler] verify if the Kafka event is new ? id: {} ", jobOfferCreatedEvent.getId());
        try {
            if(jobOfferApplyService.isTheEventKafkaWasConsumedAlready(messageId)) {
                LOGGER.info("[JobOfferCreatedEventHandler] received a new event: {} ", jobOfferCreatedEvent.getName());
                processedEvent.setMessageId(jobOfferCreatedEvent.getId());
                List<Candidate> candidateList = this.candidateService.getCandidate();

                for (Candidate candidate : candidateList) {
                    // Get skills of the candidate & offer
                    List<String> candidateSkills = candidate.getSkills();
                    List<String> jobOfferSkills = jobOfferCreatedEvent.getSkills();

                    // Calculate matching percentage
                    double matchingPercentage = jobOfferApplyService.calculateMatchingPercentage(jobOfferSkills, candidateSkills);

                    // If matching percentage is >= 50%, send an email
                    if (matchingPercentage >= 50.0) {
                        LOGGER.info("[JobOfferCreatedEventHandler] Congratulations candidate: {} ! " +
                                "You are eligible for the job offer based on your skills match", candidate.getName());
                        ApplyActionHistory applyActionHistory = new ApplyActionHistory();
                        applyActionHistory.setJobOfferId(jobOfferCreatedEvent.getId());
                        applyActionHistory.setCandidateId(candidate.getId());
                        jobOfferApplyService.saveJobOfferApply(applyActionHistory);
                        this.emailService.sendCandidateApplicationEmail(jobOfferCreatedEvent, candidate);
                        LOGGER.info("[JobOfferCreatedEventHandler] Candidate: [{}] has applied to Job id {} ", candidate.getName(), jobOfferCreatedEvent.getId());

                    } else {
                        // Print a message if not eligible
                        LOGGER.info("[JobOfferCreatedEventHandler] Candidate {} is not eligible, matching procentage is {}", candidate.getName(), matchingPercentage);

                    }
                }
                jobOfferApplyService.saveJobOfferEventKafka(processedEvent);
                LOGGER.info("[JobOfferCreatedEventHandler] event kafka saved to bdd, messageId : {}", messageId);

            }else
                LOGGER.info("[JobOfferCreatedEventHandler] event kafka already consumed, messageId : {}", messageId);
        } catch (DataIntegrityViolationException  | MailException | HibernateException | JpaSystemException |
                 InvalidDataAccessResourceUsageException ex) {
            throw new NotRetryableException(ex);

        }

    }


}
