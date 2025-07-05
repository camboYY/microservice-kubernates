package com.easybytes.accounts.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "accounts")
public class AccountsConfiguration {
    private String message;
    private Map<String, String> contactDetails;
    private Map<String, String> onCallSupport;
}
