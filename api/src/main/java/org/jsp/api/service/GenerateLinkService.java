package org.jsp.api.service;

import org.jsp.api.dao.MerchantDao;
import org.jsp.api.dao.UserDao;
import org.jsp.api.dto.Merchant;
import org.jsp.api.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;

@Service
public class GenerateLinkService {
	@Autowired
	private MerchantDao mDao;
	
	@Autowired
	private UserDao uDao;

	public String getVerificationLink(HttpServletRequest request, Merchant merchant) {
		String token = RandomString.make(45);
		merchant.setToken(token);
		merchant.setStatus("Inactive");
		mDao.updateMerchant(merchant);
		String siteurl = request.getRequestURI().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String verify_link = url + "/merchants/verify?token=" + token;
		return verify_link;
	}
	
	public String getResetPasswordLink(HttpServletRequest request, Merchant merchant) {
		String token = RandomString.make(45);
		merchant.setToken(token);
		mDao.updateMerchant(merchant);
		String siteurl = request.getRequestURI().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String reset_link = url + "/merchants/reset-password?token=" + token;
		return reset_link;
	}
	
	public String getVerificationLink(HttpServletRequest request, User user) {
		String token = RandomString.make(45);
		user.setToken(token);
		user.setStatus("Inactive");
		uDao.updateUser(user);
		String siteurl = request.getRequestURI().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String verify_link = url + "/users/verify?token=" + token;
		return verify_link;
	}
	
	public String getResetPasswordLink(HttpServletRequest request, User user) {
		String token = RandomString.make(45);
		user.setToken(token);
		uDao.updateUser(user);
		String siteurl = request.getRequestURI().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String reset_link = url + "/users/reset-password?token=" + token;
		return reset_link;
	}
}
