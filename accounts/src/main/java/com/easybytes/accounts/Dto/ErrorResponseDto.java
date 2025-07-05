package com.easybytes.accounts.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponseDto", description = "Error Response Dto to hold error details")
public class ErrorResponseDto {
    @Schema(name = "apiPath", description = "API path of the error")
    private String apiPath;
    @Schema(name = "statusCode", description = "Status code of the error")
    private HttpStatus errorCode;
    @Schema(name = "errorMessage", description = "Error message of the error")
    private String errorMessage;
    @Schema(name = "errorTime", description = "Time of the error")
    private LocalDateTime errorTime;
}
