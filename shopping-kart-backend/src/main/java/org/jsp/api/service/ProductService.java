package org.jsp.api.service;

import java.util.List;
import java.util.Optional;

import org.jsp.api.dao.MerchantDao;
import org.jsp.api.dao.ProductDao;
import org.jsp.api.dao.UserDao;
import org.jsp.api.dto.Merchant;
import org.jsp.api.dto.Product;
import org.jsp.api.dto.ResponseStructure;
import org.jsp.api.dto.User;
import org.jsp.api.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductService {
	@Autowired
	private ProductDao pDao;

	@Autowired
	private UserDao uDao;

	@Autowired
	private MerchantDao mDao;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(@RequestBody Product product, int mId) {
		ResponseStructure<Product> structure = new ResponseStructure<>();
		Optional<Merchant> recMerchant = mDao.findById(mId);
		if (recMerchant.isPresent()) {
			recMerchant.get().getProducts().add(product);
			product.setMerchant(recMerchant.get());
			pDao.saveProduct(product);
			mDao.updateMerchant(recMerchant.get());
			structure.setData(product);
			structure.setStatusCode(HttpStatus.CREATED.value());
			structure.setMessage("Product added");
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.CREATED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody Product product, int mId) {
		ResponseStructure<Product> structure = new ResponseStructure<>();
		Optional<Merchant> recMerchant = mDao.findById(mId);
		if (recMerchant.isPresent()) {
			recMerchant.get().getProducts().add(product);
			product.setMerchant(recMerchant.get());
			structure.setData(pDao.updateProduct(product));
			mDao.updateMerchant(recMerchant.get());
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Product updated");
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.ACCEPTED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Product>> findById(int id) {
		Optional<Product> recProduct = pDao.findById(id);
		ResponseStructure<Product> structure = new ResponseStructure<>();
		if (recProduct.isPresent()) {
			structure.setData(recProduct.get());
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Product found");
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<String>> deleteProduct(int id) {
		Optional<Product> recProduct = pDao.findById(id);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (recProduct.isPresent()) {
			pDao.deleteProduct(id);
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Product deleted");
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByMerchantId(int merchant_id) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(pDao.findProductsByMerchantId(merchant_id));
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("Product loaded");
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByBrand(String brand) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(pDao.findProductsByBrand(brand));
		structure.setMessage("The product brand found");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByCategory(String category) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(pDao.findProductsByCategory(category));
		structure.setMessage("The product category found");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<String>> addToCart(int product_id, int user_id) {
		Optional<User> recUser = uDao.findById(user_id);
		Optional<Product> recProduct = pDao.findById(product_id);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (recUser.isPresent() && recProduct.isPresent()) {
			recUser.get().getCart().add(recProduct.get());
			uDao.updateUser(recUser.get());
			structure.setData("Product added to the cart");
			structure.setMessage("User and product found");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		structure.setData("Cannot add product to the cart");
		structure.setMessage("Invalid user id or product id");
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseStructure<String>> addToWishList(int product_id, int user_id) {
		Optional<User> recUser = uDao.findById(user_id);
		Optional<Product> recProduct = pDao.findById(product_id);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (recUser.isPresent() && recProduct.isPresent()) {
			recUser.get().getWishList().add(recProduct.get());
			uDao.updateUser(recUser.get());
			structure.setData("Product added to the wishList");
			structure.setMessage("User and product found");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		structure.setData("Cannot add product to the WishList");
		structure.setMessage("Invalid user id or product id");
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseStructure<Product>> rateProduct(int product_id, int user_id, double rating) {
		Optional<User> recUser = uDao.findById(user_id);
		Optional<Product> recProduct = pDao.findById(product_id);
		ResponseStructure<Product> structure = new ResponseStructure<>();
		if (recUser.isPresent() && recProduct.isPresent()) {
			Product product = recProduct.get();
			int n = product.getNo_of_users();
			double r = product.getRating() * n++;
			rating = (r + rating) / n;
			product.setNo_of_users(n);
			product.setRating(rating);
			pDao.updateProduct(product);
			structure.setData(product);
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Product rated");
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.ACCEPTED);
		}
		structure.setData(null);
		structure.setMessage("Cannot rate product");
		structure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.NOT_ACCEPTABLE);
	}
	
	public ResponseEntity<ResponseStructure<List<Product>>> findProductsInCart(int id) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(pDao.findProductsInCart(id));
		structure.setMessage("Following are the list of products in cart");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Product>>> findProductsInWishList(int id) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(pDao.findProductsInWishList(id));
		structure.setMessage("Following are the list of products in wishlist");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}
}
