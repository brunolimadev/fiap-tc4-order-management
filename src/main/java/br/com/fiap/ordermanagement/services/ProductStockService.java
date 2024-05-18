package br.com.fiap.ordermanagement.services;

import br.com.fiap.ordermanagement.models.product.request.ProductRequest;
import br.com.fiap.ordermanagement.models.product.response.ProductResponse;

public interface ProductStockService {

    public ProductResponse checkStock(Integer productId);

    public void updateStock(ProductRequest product);

}
