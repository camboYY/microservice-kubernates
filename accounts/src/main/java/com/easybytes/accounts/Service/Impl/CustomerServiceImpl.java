package com.easybytes.accounts.Service.Impl;

import com.easybytes.accounts.Dto.AccountDto;
import com.easybytes.accounts.Dto.CardsDto;
import com.easybytes.accounts.Dto.CustomerDetailDto;
import com.easybytes.accounts.Dto.LoansDto;
import com.easybytes.accounts.Entity.Accounts;
import com.easybytes.accounts.Entity.Customer;
import com.easybytes.accounts.Exception.ResourceNotFoundException;
import com.easybytes.accounts.Mapper.AccountsMapper;
import com.easybytes.accounts.Mapper.CustomerMapper;
import com.easybytes.accounts.Repository.AccountRepository;
import com.easybytes.accounts.Repository.CustomerRepository;
import com.easybytes.accounts.Service.ICustomerService;
import com.easybytes.accounts.Service.client.CardsFeignClient;
import com.easybytes.accounts.Service.client.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient, AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Fetches the customer details for a given mobile number.
     *
     * @param mobileNumber mobile number of the customer
     * @return the customer details if found, otherwise null
     */
    @Override
    public CustomerDetailDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId()));
        CustomerDetailDto customerDetailDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailDto());
        customerDetailDto.setAccountDto(AccountsMapper.mapToAccountsDto(accounts, new AccountDto()));
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailDto.setCardsDto(cardsDtoResponseEntity.getBody());
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailDto.setLoansDto(loansDtoResponseEntity.getBody());
        return customerDetailDto;
    }
}
