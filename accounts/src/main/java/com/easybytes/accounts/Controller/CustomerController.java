package com.easybytes.accounts.Controller;


import com.easybytes.accounts.Dto.CustomerDetailDto;
import com.easybytes.accounts.Service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Customer API for Customer Details", description = "The Customer Details API")
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public ResponseEntity<CustomerDetailDto> fetchCustomerDetails(@RequestParam @Pattern(regexp = "[0-9]{10}",message = "mobile number should be 10 digits") String mobileNumber) {
       CustomerDetailDto customerDetailDto = customerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailDto);
    }
}
