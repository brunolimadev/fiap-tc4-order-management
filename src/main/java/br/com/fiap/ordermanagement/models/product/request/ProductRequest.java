package br.com.fiap.ordermanagement.models.product.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    public int id;
    public String description;
    public String price;
    public String storeQuantity;
    public LocalDateTime createDateTime;
    public LocalDateTime updateDateTime;
}
