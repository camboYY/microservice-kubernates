package com.easybytes.accounts.Service.Impl;

import com.easybytes.accounts.Constants.AccountsConstants;
import com.easybytes.accounts.Service.IAccountService;
import com.easybytes.accounts.Dto.AccountDto;
import com.easybytes.accounts.Dto.CustomerDto;
import com.easybytes.accounts.Entity.Accounts;
import com.easybytes.accounts.Entity.Customer;
import com.easybytes.accounts.Exception.CustomerAlreadyExistsException;
import com.easybytes.accounts.Exception.ResourceNotFoundException;
import com.easybytes.accounts.Mapper.AccountsMapper;
import com.easybytes.accounts.Mapper.CustomerMapper;
import com.easybytes.accounts.Repository.AccountRepository;
import com.easybytes.accounts.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    /**
     *
     * @param customerDto - customerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exist");
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * Fetches the account detail for the given mobile number
     * @param mobileNumber mobile number of the customer
     * @return the account details if found, otherwise threw ResourceNotFoundException
     */
    @Override
    public CustomerDto fetchAccountDetail(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountsMapper.mapToAccountsDto(accounts, new AccountDto()));
        return customerDto;
    }

    /**
     * Updates the account details for the given customer.
     * @param customerDto the customer details to be updated
     * @return true if the update is successful, otherwise false
     */
    @Override
    public boolean updateAccountDetail(CustomerDto customerDto) {
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto != null) {
            Accounts accounts =accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber()));
            AccountsMapper.mapToAccounts(accountDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
            CustomerMapper.mapToCustomer(customerDto, customer);
             customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public void deleteCustomerBy(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Long customerId = customer.getCustomerId();
        accountRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }

    /**
     *
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
