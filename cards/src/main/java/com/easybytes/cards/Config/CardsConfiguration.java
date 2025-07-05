package com.easybytes.cards.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "cards")
public class CardsConfiguration {
    private String message;
    private Map<String, String> contactDetails;
    private Map<String, String> onCallSupport;
}
