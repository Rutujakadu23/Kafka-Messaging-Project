package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.config.AppConstants;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    public boolean updatelocation(String Location) {
        kafkaTemplate.send(AppConstants.LOCATION_TOPIC_NAME, Location);
        logger.info("Message produced: {}", Location);
       
        return true;
    }
}
