package com.ecommerce.product_ms.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.ecommerce.product_ms.exceptions.ProductNotFoundException;
import com.ecommerce.product_ms.exceptions.InsufficientQuantityException;
import com.ecommerce.product_ms.models.Product;
import com.ecommerce.product_ms.models.DetailOrder;
import com.ecommerce.product_ms.repositories.ProductRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
/*
 * @RequestMapping("/productos")
 */
public class ProductController {
    /*
     * @Autowired ProductoService productService;
     */

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;

        // Seed
        /*
         * Product newProduct01 = new Product("001", "DDR4 8G (3200) CRUCIAL BALLISTIX",
         * "RAM DESKTOP DDR4 3200MHZ", 20, "Memories", 210000); Product newProduct02 =
         * new Product("002", "DDR4 8G (3400) CRUCIAL BALLISTIX",
         * "RAM DESKTOP DDR4 3400MHZ", 30, "Memories", 260000);
         * 
         * this.productRepository.save(newProduct01);
         * this.productRepository.save(newProduct02);
         */

    }

    @GetMapping("/products/{product_Id}")
    Product getProduct(@PathVariable String product_Id) {
        return productRepository.findById(product_Id).orElseThrow(
                () -> new ProductNotFoundException("No se encontro ningun producto con el Id: " + product_Id));
    }

    @GetMapping("/allproducts")
    List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @PutMapping("/neworder")
    public Map<String, String> newOrder(@RequestBody ArrayList<DetailOrder> detailOrder) {
        HashMap<String, String> message = new HashMap<>();
        ArrayList<Product> orderProd = new ArrayList<>();
        detailOrder.forEach((detail) -> {
            Product prod = productRepository.findById(detail.getIdProduct())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "No se encontro ningun producto con el Id: " + detail.getIdProduct()));
            if (prod.getQuantity() < detail.getQuantity()) {
                throw new InsufficientQuantityException("No hay cantidad suficiente");
            } else {
                int newQuant = prod.getQuantity() - detail.getQuantity();
                prod.setQuantity(newQuant);
                orderProd.add(prod);
                orderProd.forEach((producto) -> {
                    productRepository.save(producto);
                });
                message.put("response", "Inventario actualizado");
            }
        });
        return message;
    }

    @PostMapping("/newproduct")
    Product newProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/delproduct/{product_Id}")
    List<Product> delProduct(@PathVariable String product_Id) {
        productRepository.deleteById(product_Id);
        return productRepository.findAll();
    }

    @PutMapping("/updproduct/{product_Id}")
    Product updProduct(@PathVariable String product_Id, @RequestBody Product productDetails) {
        Product prod = productRepository.findById(product_Id).orElseThrow(
                () -> new ProductNotFoundException("No se encontro ningun producto con el Id: " + product_Id));
        prod.setUrl(productDetails.getUrl());
        prod.setName(productDetails.getName());
        prod.setCategory(productDetails.getCategory());
        prod.setDescription(productDetails.getDescription());
        prod.setProduct_Id(productDetails.getProduct_Id());
        prod.setPrice(productDetails.getPrice());
        prod.setQuantity(productDetails.getQuantity());
        return productRepository.save(prod);
    }

}
