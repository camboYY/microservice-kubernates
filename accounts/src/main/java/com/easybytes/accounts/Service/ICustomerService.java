package com.easybytes.accounts.Service;

import com.easybytes.accounts.Dto.CustomerDetailDto;

public interface ICustomerService {
    public CustomerDetailDto fetchCustomerDetails(String mobileNumber);
}
