package com.joboffer.consumer.ws.emailnotification.repository;

import com.joboffer.ws.core.jpa.entities.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Long> {

    ProcessedEvent findByMessageId(String messageId);


}
