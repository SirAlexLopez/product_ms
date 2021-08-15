package com.ecommerce.product_ms.repositories;
import com.ecommerce.product_ms.models.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository <Product, String> {
}
