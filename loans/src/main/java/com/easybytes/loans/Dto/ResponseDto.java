package com.easybytes.loans.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "ResponseDto", description = "Schema to hold successful information")
public class ResponseDto {
    @Schema(name = "statusCode", description = "Status code of the response")
    private String statusCode;

    @Schema(name = "statusMsg", description = "Status message of the response")
    private String statusMsg;
}
