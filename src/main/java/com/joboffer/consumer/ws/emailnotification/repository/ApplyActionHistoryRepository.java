package com.joboffer.consumer.ws.emailnotification.repository;

import com.joboffer.consumer.ws.emailnotification.entity.ApplyActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyActionHistoryRepository extends JpaRepository<ApplyActionHistory, Long> {
}
