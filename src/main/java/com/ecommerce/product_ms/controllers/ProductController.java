package com.ecommerce.product_ms.controllers;

import java.util.List;

import com.ecommerce.product_ms.exceptions.ProductNotFoundException;
import com.ecommerce.product_ms.models.Product;
import com.ecommerce.product_ms.repositories.ProductRepository;
import com.ecommerce.product_ms.services.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
/*
@RequestMapping("/productos")*/
public class ProductController {
    /*@Autowired 
    ProductoService productService;*/

    
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;

        //Seed
        Product newProduct01 = new Product("001", "DDR4 8G (3200) CRUCIAL BALLISTIX", "RAM DESKTOP DDR4 3200MHZ",20,"Memories",210000);
        Product newProduct02 = new Product("002", "DDR4 8G (3400) CRUCIAL BALLISTIX", "RAM DESKTOP DDR4 3400MHZ",30,"Memories",260000);

        this.productRepository.save(newProduct01);
        this.productRepository.save(newProduct02);

    }


    @GetMapping("/products/{product_Id}")
    Product getProduct(@PathVariable String product_Id){
        return productRepository.findById(product_Id)
                .orElseThrow(() -> new ProductNotFoundException("No se encontro ningun producto con el Id: " + product_Id));
    }

    @GetMapping("/allproducts")
    List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @PostMapping("/newproduct")
    Product newProduct(@RequestBody Product product){
            return productRepository.save(product);
    }
}
