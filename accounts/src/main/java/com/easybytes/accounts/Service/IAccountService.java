package com.easybytes.accounts.Service;

import com.easybytes.accounts.Dto.CustomerDto;

public interface IAccountService {
    /**
     * @param customerDto - customerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Fetches the account detail for the given mobile number
     *
     * @param mobileNumber mobile number of the customer
     * @return the account details if found, otherwise null
     */
    CustomerDto fetchAccountDetail(String mobileNumber);

    /**
     * Updates the account details for the given customer.
     *
     * @param customerDto the customer details to be updated
     * @return true if the update is successful, otherwise false
     */
    boolean updateAccountDetail(CustomerDto customerDto);

    /**
     * Deletes the customer with the given mobile number.
     *
     * @param mobileNumber mobile number of the customer to delete
     */
    void deleteCustomerBy(String mobileNumber);
}