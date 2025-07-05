package com.easybytes.accounts.Audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * @return the current auditor, which is the name of the service that is making the request.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNT_MS");
    }
}
