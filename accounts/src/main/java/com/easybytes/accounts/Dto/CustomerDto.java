package com.easybytes.accounts.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer and Account information")
public class CustomerDto {
    @Schema(name = "name", description = "Name of the customer")
    @NotEmpty(message = "name should not be empty")
    @Size(min=2,max = 30, message = "name should be between 2 and 30 characters")
    private String name;

    @Schema(name = "email", description = "Email address of the customer")
    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Schema(name = "mobileNumber", description = "Mobile number of the customer")
    @NotEmpty(message = "mobile number should not be empty")
    @Pattern(regexp = "[0-9]{10}", message = "mobile number should be 10 digits")
    private String mobileNumber;

    @Schema(name = "account", description = "Account details of the customer")
    private AccountDto accountDto;
}
