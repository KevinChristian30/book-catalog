package com.learning.bookcatalog.service.impl;

import org.springframework.stereotype.Service;

import com.learning.bookcatalog.config.ApplicationProperties;
import com.learning.bookcatalog.config.CloudProperties;
import com.learning.bookcatalog.service.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {
    private ApplicationProperties applicationProperties;
    private CloudProperties cloudProperties;

    public GreetingServiceImpl(
        ApplicationProperties applicationProperties, 
        CloudProperties cloudProperties
    ) {
        super();
        this.applicationProperties = applicationProperties;
        this.cloudProperties = cloudProperties;
    }

    @Override
    public String sayGreeting() {
        return applicationProperties.getWelcomeText() + " " + applicationProperties.getTimezone() + " " + applicationProperties.getCurrency() + " " + cloudProperties.getApiKey();
    }
    
}
