package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.consumer.ws.emailnotification.repository.CandidateRepository;
import com.joboffer.ws.core.jpa.entities.Candidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    List<Candidate> getCandidate(){
        return this.candidateRepository.findAll();
    }
}
