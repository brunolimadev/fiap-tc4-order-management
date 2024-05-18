package br.com.fiap.ordermanagement.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.ordermanagement.models.product.request.ProductRequest;
import br.com.fiap.ordermanagement.models.product.response.ProductResponse;
import br.com.fiap.ordermanagement.services.ProductStockService;

@Service
public class ProductStockServiceImpl implements ProductStockService {

    @Value("${product.stock.url}")
    private String productStockUrl;

    private final RestTemplate restTemplate;

    public ProductStockServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponse checkStock(Integer productId) {

        var url = productStockUrl +
                "/" +
                productId;

        var result = restTemplate.getForObject(url, ProductResponse.class);
        return result;
    }

    @Override
    public void updateStock(ProductRequest product) {
        var url = productStockUrl +
                "/" +
                product.getId() +
                "/stocks";

        restTemplate.put(url, product);
    }

}
