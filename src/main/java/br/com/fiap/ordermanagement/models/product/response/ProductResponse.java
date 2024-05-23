package br.com.fiap.ordermanagement.models.product.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    public int id;
    public String description;
    public String price;
    public String storeQuantity;
    public String createDateTime;
    public String updateDateTime;
}
