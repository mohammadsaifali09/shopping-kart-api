package org.jsp.api.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.api.dto.Product;
import org.jsp.api.repository.ProductRepository;
import org.jsp.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Optional<Product> findById(int id) {
		return productRepository.findById(id);
	}
	
	public void deleteProduct(Integer id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> findProductsByMerchantId(int merchant_id){
		return productRepository.findProductsByMerchantId(merchant_id);
	}
	
	public List<Product> findProductsByBrand(String brand){
		return productRepository.findProductsByBrand(brand);
	}
	
	public List<Product> findProductsByCategory(String category){
		return productRepository.findProductsByCategory(category);
	}
	
	public List<Product> findProductsInCart(int id){
		return userRepository.findProductsInCart(id);
	}
	
	public List<Product> findProductsInWishList(int id){
		return userRepository.findProductsInWishList(id);
	}
}
