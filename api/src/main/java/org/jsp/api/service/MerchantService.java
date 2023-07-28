package org.jsp.api.service;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jsp.api.dao.MerchantDao;
import org.jsp.api.dto.EmailConfiguration;
import org.jsp.api.dto.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
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

	public Merchant saveMerchant(HttpServletRequest request, Merchant merchant) {
		configuration.setSubject("Registraion succesful");
		dao.saveMerchant(merchant);
		HashMap<String, String> map = new LinkedHashMap<>();
		map.put("email", merchant.getEmail());
		map.put("name", merchant.getName());
		configuration.setText("Hello Mr. " + merchant.getName()
				+ " You have succesfully initiated the registration for our E-commerce application "
				+ "please click on the link " + service.getVerificationLink(request, merchant));
		configuration.setUser(map);
		mailService.sendMail(configuration);
		return merchant;
	}

	public String verifyMerchant(String token) {
		Merchant merchant = dao.verifyMerchant(token);
		if (merchant != null) {
			merchant.setToken(null);
			merchant.setStatus("Active");
			dao.updateMerchant(merchant);
			return "Your account is activated";
		}
		return "Invalid token";
	}

	public String sendResetPasswordLink(HttpServletRequest request, String email) {
		Merchant merchant = dao.findMerchantByEmail(email);
		if (merchant != null) {
			HashMap<String, String> map = new LinkedHashMap<>();
			map.put("email", merchant.getEmail());
			map.put("name", merchant.getName());
			configuration.setSubject("Reset Password");
			configuration.setUser(map);
			configuration.setText("Hello Mr. " + merchant.getName()
					+ " You have requested for password change please click on the link "
					+ service.getResetPasswordLink(request, merchant));
			mailService.sendMail(configuration);
			return "Reset password link sent to email";
		}
		return "Please register";
	}
}
