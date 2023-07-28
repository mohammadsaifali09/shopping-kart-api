package org.jsp.api.service;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jsp.api.dao.UserDao;
import org.jsp.api.dto.EmailConfiguration;
import org.jsp.api.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
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

	public User saveUser(HttpServletRequest request, User user) {
		configuration.setSubject("Registraion succesful");
		dao.saveUser(user);
		HashMap<String, String> map = new LinkedHashMap<>();
		map.put("email", user.getEmail());
		map.put("name", user.getName());
		configuration.setText("Hello Mr. " + user.getName()
				+ " You have succesfully initiated the registration for our E-commerce application "
				+ "please click on the link " + service.getVerificationLink(request, user));
		configuration.setUser(map);
		mailService.sendMail(configuration);
		return user;
	}

	public String verifyUser(String token) {
		User user = dao.verifyUser(token);
		if (user != null) {
			user.setToken(null);
			user.setStatus("Active");
			dao.updateUser(user);
			return "Your account is activated";
		}
		return "Invalid token";
	}

	public String sendResetPasswordLink(HttpServletRequest request, String email) {
		User user = dao.findUserByEmail(email);
		if (user != null) {
			HashMap<String, String> map = new LinkedHashMap<>();
			map.put("email", user.getEmail());
			map.put("name", user.getName());
			configuration.setSubject("Reset Password");
			configuration.setUser(map);
			configuration.setText("Hello Mr. " + user.getName()
					+ " You have requested for password change please click on the link "
					+ service.getResetPasswordLink(request, user));
			mailService.sendMail(configuration);
			return "Reset password link sent to email";
		}
		return "Please register";
	}
}
