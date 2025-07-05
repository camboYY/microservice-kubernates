package com.easybytes.accounts.Repository;

import com.easybytes.accounts.Entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(Long customerId);

    /**
     * Deletes all accounts associated with the given customerId.
     * @param customerId The id of the customer whose accounts to delete
     */
    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
