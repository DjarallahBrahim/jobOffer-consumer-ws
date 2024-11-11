package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.ws.core.JobOfferCreatedEvent;
import com.joboffer.ws.core.jpa.entities.Candidate;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendCandidateApplicationEmail(JobOfferCreatedEvent jobOfferCreatedEvent, Candidate candidate);
}
