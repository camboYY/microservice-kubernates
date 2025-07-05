package com.easybytes.loans.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "loans")
public class LoansConfiguration {
    private String message;
    private Map<String, String> contactDetails;
    private Map<String, String> onCallSupport;
}
