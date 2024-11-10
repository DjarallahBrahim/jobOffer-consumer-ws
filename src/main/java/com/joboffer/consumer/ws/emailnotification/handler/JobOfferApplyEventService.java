package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.consumer.ws.emailnotification.repository.ApplyActionHistoryRepository;
import com.joboffer.consumer.ws.emailnotification.repository.ProcessedEventRepository;
import com.joboffer.ws.core.jpa.entities.ApplyActionHistory;
import com.joboffer.ws.core.jpa.entities.ProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobOfferApplyEventService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ApplyActionHistoryRepository applyActionHistoryRepository;
    private ProcessedEventRepository processedEventRepository;

    public JobOfferApplyEventService(ApplyActionHistoryRepository applyActionHistoryRepository,
                                     ProcessedEventRepository processedEventRepository) {
        this.applyActionHistoryRepository = applyActionHistoryRepository;
        this.processedEventRepository = processedEventRepository;
    }


    @Transactional
    public void saveJobOfferEventKafka (ProcessedEvent processedEvent){
        this.processedEventRepository.save(processedEvent);
    }
    @Transactional
    public void saveJobOfferApply (ApplyActionHistory applyActionHistory){
        this.applyActionHistoryRepository.save(applyActionHistory);
    }

    @Transactional
    public boolean isTheEventKafkaWasConsumedAlready(String processedEventId) {
        ProcessedEvent processedEvent = this.processedEventRepository.findByMessageId(processedEventId);
        LOGGER.info("[JobOfferApplyEventService] processedEvent found is {}", processedEvent);
        return this.processedEventRepository.findByMessageId(processedEventId) == null;
    }

    public double calculateMatchingPercentage(List<String> jobOfferSkills, List<String> candidateSkills) {
        if (jobOfferSkills.isEmpty() || candidateSkills.isEmpty()) {
            return 0.0;
        }

        // Calculate number of matching skills
        long matchingSkillsCount = jobOfferSkills.stream()
                .filter(candidateSkills::contains)
                .count();

        // Calculate the percentage of matching skills
        return (double) matchingSkillsCount / jobOfferSkills.size() * 100;
    }
}
