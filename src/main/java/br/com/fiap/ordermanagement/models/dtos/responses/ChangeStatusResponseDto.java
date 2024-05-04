package br.com.fiap.ordermanagement.models.dtos.responses;

import br.com.fiap.ordermanagement.models.OrderHistory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeStatusResponseDto {

    private String message;

    public static ChangeStatusResponseDto fromEntity(OrderHistory result) {

        return ChangeStatusResponseDto.builder()
                .message("Status has been changed for order: [ " + result.getOrderId() + " ] " + " => " + result.getStatus())
                .build();

    }
}
