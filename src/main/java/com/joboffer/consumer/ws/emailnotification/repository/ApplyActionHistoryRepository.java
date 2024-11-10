package com.joboffer.consumer.ws.emailnotification.repository;

import com.joboffer.ws.core.jpa.entities.ApplyActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyActionHistoryRepository extends JpaRepository<ApplyActionHistory, Long> {
}
