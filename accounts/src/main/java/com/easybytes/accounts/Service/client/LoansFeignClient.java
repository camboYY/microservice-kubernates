package com.easybytes.accounts.Service.client;

import com.easybytes.accounts.Dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {
    
    /**
     * Retrieves the card details for a customer based on the given mobile number.
     *
     * @param mobileNumber the mobile number of the customer whose card details are to be fetched
     * @return a ResponseEntity containing the CardsDto with card details if found,
     *         otherwise a 404 status if the customer or card is not found
     */
    @GetMapping(value = "/api/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);
}
