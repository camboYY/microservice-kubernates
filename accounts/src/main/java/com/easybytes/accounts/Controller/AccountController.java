package com.easybytes.accounts.Controller;

import com.easybytes.accounts.Config.AccountsConfiguration;
import com.easybytes.accounts.Constants.AccountsConstants;
import com.easybytes.accounts.Dto.ErrorResponseDto;
import com.easybytes.accounts.Service.IAccountService;
import com.easybytes.accounts.Dto.CustomerDto;
import com.easybytes.accounts.Dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CRUD REST API for Account Microservice", description = "The Account API")
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {

    private final IAccountService accountService;
    private final Environment environment;
    private final AccountsConfiguration globalConfiguration;
    @Value("${build.version}")
    private String buildVersion;

    public AccountController(IAccountService accountService, Environment environment, AccountsConfiguration globalConfiguration) {
        this.accountService = accountService;
        this.environment = environment;
        this.globalConfiguration = globalConfiguration;
    }

    /**
     * Creates a new account for a customer. The customer details are provided in the request body.
     *
     * @param customerDto the customer details
     * @return a ResponseEntity with a 201 status and a response body containing the status and success message
     *         if the account is created successfully, otherwise a 400 status is returned if the customer
     *         already exists
     */
    @Operation(summary = "Creates a new account for a customer", description = "The customer details are provided in the request body")
   @ApiResponse(responseCode = "201", description = "Account created successfully")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    /**
     * Retrieves the account detail for a customer based on the provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer whose account details are to be fetched
     * @return a ResponseEntity containing the CustomerDto with account details if found,
     *         otherwise returns a 404 status if the customer or account is not found
     */
    @Operation(summary = "Retrieves the account detail for a customer based on the provided mobile number")
    @ApiResponse(responseCode = "200", description = "Account details fetched successfully")
    @GetMapping("/detail")
    public ResponseEntity<CustomerDto> fetchAccountDetail(@RequestParam
                                                             @Pattern(regexp =  "[0-9]{10}", message = "mobile number should be 10 digits") String mobileNumber) {
       CustomerDto customerDto = accountService.fetchAccountDetail(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    /**
     * Updates the account details for a customer. The customer details are provided in the request body.
     *
     * @param customerDto the customer details
     * @return a ResponseEntity with a 200 status and a response body containing the status and success message
     *         if the account details are updated successfully, otherwise a 400 status is returned if the update
     *         operation fails
     */
    @Operation(summary = "Updates the account details for a customer", description = "The customer details are provided in the request body")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account details updated successfully"),
        @ApiResponse(responseCode = "400", description = "Update operation failed", content = @Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDto.class)
        ))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetail(@Valid @RequestBody CustomerDto customerDto) {
       boolean isUpdated = accountService.updateAccountDetail(customerDto);
       if(isUpdated) {
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
       } else {
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body(new ResponseDto(AccountsConstants.STATUS_400, AccountsConstants.MESSAGE_400));
       }
    }

    /**
     * Deletes the account associated with the provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer whose account is to be deleted
     * @return a ResponseEntity with a 200 status and a response body containing the status and success message
     *         if the account is deleted successfully
     */
    @Operation(summary = "Deletes the account associated with the provided mobile number", description = "The account is deleted successfully")
    @ApiResponse(responseCode = "200", description = "Account deleted successfully")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetail(@RequestParam @Pattern(regexp =  "[0-9]{10}", message = "mobile number should be 10 digits") String mobileNumber) {
        accountService.deleteCustomerBy(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));

    }

    @Operation(summary = "Get build information that is deployed in microservice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get build version"),
            @ApiResponse(responseCode = "400", description = "Get build version failed", content = @Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @GetMapping("/getBuildInfo")
    public ResponseEntity<AccountsConfiguration> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(globalConfiguration);
    }

    @GetMapping("/getBuildVersion")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("build.version"));
    }
}
