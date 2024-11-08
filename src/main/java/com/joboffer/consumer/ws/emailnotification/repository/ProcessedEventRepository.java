package com.joboffer.consumer.ws.emailnotification.repository;

import com.joboffer.consumer.ws.emailnotification.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Long> {

    ProcessedEvent findByMessageId(String messageId);


}
