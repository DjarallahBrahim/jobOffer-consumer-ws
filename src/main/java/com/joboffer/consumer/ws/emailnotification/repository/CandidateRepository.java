package com.joboffer.consumer.ws.emailnotification.repository;

import com.joboffer.ws.core.jpa.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
