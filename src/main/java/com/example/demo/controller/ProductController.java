package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ResourceNotFoundException;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/products/")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@PostMapping("/admin/addProduct")
	public Product createProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@GetMapping("/all")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not exists with id: " + id));
		return ResponseEntity.ok(product);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not exists with id: " + id));
		
		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		product.setPrice(productDetails.getPrice());
		
		Product updatedProduct = productRepository.save(product);
		return ResponseEntity.ok(updatedProduct);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String , Boolean>> deleteProduct(@PathVariable Integer id){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not exists with id: " + id));
		
		productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
		
}