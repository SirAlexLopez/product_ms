package com.ecommerce.product_ms.services;

import java.util.ArrayList;

import com.ecommerce.product_ms.models.Product;
import com.ecommerce.product_ms.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

	@Autowired
	ProductRepository productorepository;
	
	public ArrayList<Product> obtenerProductos(){
		return (ArrayList<Product>) productorepository.findAll();
		
	}

	public Product guardarproducto(Product producto) {
		return productorepository.save(producto);
	}
	
	
}
