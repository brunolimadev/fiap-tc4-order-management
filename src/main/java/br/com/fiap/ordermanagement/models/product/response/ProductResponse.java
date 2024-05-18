package br.com.fiap.ordermanagement.models.product.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductResponse {
    public int id;
    public String description;
    public String price;
    public String storeQuantity;
    public LocalDateTime createDateTime;
    public LocalDateTime updateDateTime;
}
