package br.com.fiap.ordermanagement.models.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeStatusRequestDto {
    private Integer status;
    private String description;
}
