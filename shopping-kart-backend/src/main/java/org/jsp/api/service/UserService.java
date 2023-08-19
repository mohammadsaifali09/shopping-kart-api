package org.jsp.api.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.jsp.api.dao.UserDao;
import org.jsp.api.dto.EmailConfiguration;
import org.jsp.api.dto.Merchant;
import org.jsp.api.dto.ResponseStructure;
import org.jsp.api.dto.User;
import org.jsp.api.exception.IdNotFoundException;
import org.jsp.api.exception.InvalidCredentialsException;
import org.jsp.api.util.MerchantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private UserDao dao;

	@Autowired
	private ShoppingCartMailService mailService;

	@Autowired
	private EmailConfiguration configuration;

	@Autowired
	private GenerateLinkService service;

	public ResponseEntity<ResponseStructure<User>> saveUser(HttpServletRequest request, User user) {
		ResponseStructure<User> structure=new ResponseStructure<>();
		structure.setData(dao.saveUser(user));
		structure.setMessage("User registered successfully");
		structure.setStatusCode(HttpStatus.CREATED.value());
		configuration.setSubject("Registraion succesful");
		HashMap<String, String> map = new LinkedHashMap<>();
		map.put("email", user.getEmail());
		map.put("name", user.getName());
		configuration.setText("Hello Mr. " + user.getName()
				+ " You have succesfully initiated the registration for Our E commerce application"
				+ " please click on the link " + service.getVerificationLink(request, user));
		configuration.setUser(map);
		mailService.sendMail(configuration);
		return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<User>> updateUser(User user) {
		ResponseStructure<User> structure = new ResponseStructure<>();
		structure.setData(dao.updateUser(user));
		structure.setStatusCode(HttpStatus.ACCEPTED.value());
		structure.setMessage("Merchant Updated");
		return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<String>> verifyUser(String token) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		User user = dao.verifyUser(token);
		if (user != null) {
			user.setToken(null);
			user.setStatus(MerchantStatus.ACTIVE.toString());
			dao.updateUser(user);
			structure.setData("Your account is activated");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("User is verified");
			return new ResponseEntity<>(structure, HttpStatus.OK);
		}
		structure.setData("Your account is not activated");
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		structure.setMessage("Invalid token");
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseStructure<String>> sendResetPasswordLink(HttpServletRequest request, String email) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		User user = dao.findUserByEmail(email);
		if (user != null) {
			HashMap<String, String> map = new LinkedHashMap<>();
			map.put("email", user.getEmail());
			map.put("name", user.getName());
			configuration.setSubject("Reset Password");
			configuration.setUser(map);
			configuration.setText("Hello Mr. " + user.getName()
					+ " You have requested for Password change please click on the following link to reset your password"
					+ " please click on the link " + service.getResetPasswordLink(request, user));
			mailService.sendMail(configuration);
			structure.setData("Reset password link sent to email");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Mail sent to user");
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		structure.setData("Please register to continue");
		structure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setMessage("No user found with the email id");
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_ACCEPTABLE);
	}
	
	public ResponseEntity<ResponseStructure<User>> verifyUser(String email, String password) {
		Optional<User> recUser = dao.verifyUser(email, password);
		ResponseStructure<User> structure = new ResponseStructure<>();
		if (recUser.isPresent()) {
			structure.setData(recUser.get());
			structure.setMessage("User found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
		}
		throw new InvalidCredentialsException();
	}
}
