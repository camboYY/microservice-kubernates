package com.easybytes.accounts.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "account", description = "Account details of the customer")
public class AccountDto {
    @Schema(name = "accountNumber", description = "Account number of the customer")
    @Pattern(regexp = "[0-9]{10}", message = "account number should be 10 digits")
    @NotEmpty(message = "account number should not be empty")
    private Long accountNumber;

    @Schema(name = "accountType", description = "Account type of the customer")
    @NotEmpty(message = "account type should not be empty")
    private String accountType;

    @Schema(name = "branchAddress", description = "Branch address of the customer")
    @NotEmpty(message = "branch address should not be empty")
    private String branchAddress;
}
