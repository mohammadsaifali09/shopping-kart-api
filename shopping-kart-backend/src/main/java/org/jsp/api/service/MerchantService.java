package org.jsp.api.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.jsp.api.dao.MerchantDao;
import org.jsp.api.dto.EmailConfiguration;
import org.jsp.api.dto.Merchant;
import org.jsp.api.dto.ResponseStructure;
import org.jsp.api.exception.IdNotFoundException;
import org.jsp.api.util.MerchantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MerchantService {
	@Autowired
	private MerchantDao dao;

	@Autowired
	private ShoppingCartMailService mailService;

	@Autowired
	private EmailConfiguration configuration;

	@Autowired
	private GenerateLinkService service;

	public ResponseEntity<ResponseStructure<Merchant>> saveMerchant(HttpServletRequest request, Merchant merchant) {
		ResponseStructure<Merchant> structure=new ResponseStructure<>();
		structure.setData(dao.saveMerchant(merchant));
		structure.setMessage("Merchant registered successfully");
		structure.setStatusCode(HttpStatus.CREATED.value());
		configuration.setSubject("Registraion succesful");
		HashMap<String, String> map = new LinkedHashMap<>();
		map.put("email", merchant.getEmail());
		map.put("name", merchant.getName());
		configuration.setText("Hello Mr. " + merchant.getName()
				+ " You have succesfully initiated the registration for Our E commerce application"
				+ " please click on the link " + service.getVerificationLink(request, merchant));
		configuration.setUser(map);
		mailService.sendMail(configuration);
		return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<String>> verifyMerchant(String token) {
		ResponseStructure<String> structure=new ResponseStructure<>();
		Merchant merchant = dao.verifyMerchant(token);
		if (merchant != null) {
			merchant.setToken(null);
			merchant.setStatus(MerchantStatus.ACTIVE.toString());
			dao.updateMerchant(merchant);
			structure.setData("Your account is activated");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Merchant is verified");
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
		}
		structure.setData("Your account is not activated");
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		structure.setMessage("Invalid token");
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseStructure<String>> sendResetPasswordLink(HttpServletRequest request, String email) {
		ResponseStructure<String> structure=new ResponseStructure<>();
		Merchant merchant = dao.findMerchantByEmail(email);
		if (merchant != null) {
			HashMap<String, String> map = new LinkedHashMap<>();
			map.put("email", merchant.getEmail());
			map.put("name", merchant.getName());
			configuration.setSubject("Reset Password");
			configuration.setUser(map);
			configuration.setText("Hello Mr. " + merchant.getName()
					+ " You have requested for Password change please click on the following link to reset your password"
					+ " please click on the link " + service.getResetPasswordLink(request, merchant));
			mailService.sendMail(configuration);
			structure.setData("Reset password link sent to email");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Mail sent to merchant");
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.ACCEPTED);
		}
		structure.setData("Please register to continue");
		structure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setMessage("No merchant found with the email id");
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_ACCEPTABLE);
	}
	
	public ResponseEntity<ResponseStructure<Merchant>> verifyMerchant(String email, String password) {
		Optional<Merchant> recUser = dao.verifyMerchant(email, password);
		ResponseStructure<Merchant> structure = new ResponseStructure<>();
		if (recUser.isPresent()) {
			structure.setData(recUser.get());
			structure.setMessage("User found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Merchant>> updateMerchant(Merchant merchant) {
		ResponseStructure<Merchant> structure = new ResponseStructure<>();
		structure.setData(dao.updateMerchant(merchant));
		structure.setStatusCode(HttpStatus.ACCEPTED.value());
		structure.setMessage("Merchant updated");
		return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.ACCEPTED);
	}
}
