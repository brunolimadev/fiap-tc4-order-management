package br.com.fiap.ordermanagement.models.error;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    String title;
    String message;

    public ErrorResponse(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
