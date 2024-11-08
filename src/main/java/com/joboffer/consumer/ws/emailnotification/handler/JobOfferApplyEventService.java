package com.joboffer.consumer.ws.emailnotification.handler;

import com.joboffer.consumer.ws.emailnotification.entity.ApplyActionHistory;
import com.joboffer.consumer.ws.emailnotification.entity.ProcessedEvent;
import com.joboffer.consumer.ws.emailnotification.repository.ApplyActionHistoryRepository;
import com.joboffer.consumer.ws.emailnotification.repository.ProcessedEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean isTheEventKafkaWasConsumedAlready(String processedEventId) {
        ProcessedEvent processedEvent = this.processedEventRepository.findByMessageId(processedEventId);
        LOGGER.info("[JobOfferApplyEventService] processedEvent found is {}", processedEvent);
        return this.processedEventRepository.findByMessageId(processedEventId) == null;
    }

}
