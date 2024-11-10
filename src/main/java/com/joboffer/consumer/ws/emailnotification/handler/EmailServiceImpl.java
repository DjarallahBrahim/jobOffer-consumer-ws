package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.ws.core.JobOfferCreatedEvent;
import com.joboffer.ws.core.jpa.entities.Candidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        LOGGER.info("[EmailServiceImpl] Sending email to=[{}]", (Object) message.getTo());

        // This will throw a MailException if there is any failure
        emailSender.send(message);

        LOGGER.info("[EmailServiceImpl] Email successfully sent to [{}]", to);
    }

    @Override
    public void sendCandidateApplicationEmail(JobOfferCreatedEvent jobOfferCreatedEvent, Candidate candidate) {
        String candidateName = candidate.getName();
        String candidatePhone = candidate.getPhone();

        String emailSubject = "New Candidate Application";
        String emailBody = String.format(
                "Dear Hiring Manager,\n\n" +
                        "I am reaching out to apply for the job position \"%s\".\n\n" +
                        "Candidate Details:\n" +
                        "Name: %s\n" +
                        "Phone: %s\n\n" +
                        "Please feel free to reach out if you would like to discuss further.\n\n" +
                        "Best regards,\n%s",
                jobOfferCreatedEvent.getName(),
                candidateName,
                candidatePhone,
                candidateName
        );

        // Send the email
        this.sendSimpleMessage(jobOfferCreatedEvent.getEmail(), emailSubject, emailBody);
    }


}
