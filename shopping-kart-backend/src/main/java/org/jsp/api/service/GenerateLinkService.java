package org.jsp.api.service;

import static org.jsp.api.util.ApplicationConstants.M_RESET_URL;
import static org.jsp.api.util.ApplicationConstants.SIZE;
import static org.jsp.api.util.ApplicationConstants.M_VERIFY_URL;

import static org.jsp.api.util.ApplicationConstants.U_RESET_URL;
import static org.jsp.api.util.ApplicationConstants.U_VERIFY_URL;

import org.jsp.api.dao.MerchantDao;
import org.jsp.api.dao.UserDao;
import org.jsp.api.dto.Merchant;
import org.jsp.api.dto.User;
import org.jsp.api.util.MerchantStatus;
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
		String token = RandomString.make(SIZE);
		merchant.setToken(token);
		merchant.setStatus(MerchantStatus.IN_ACTIVE.toString());
		mDao.updateMerchant(merchant);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		return url + M_VERIFY_URL + token;
	}

	public String getResetPasswordLink(HttpServletRequest request, Merchant merchant) {
		String token = RandomString.make(SIZE);
		merchant.setToken(token);
		mDao.updateMerchant(merchant);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		return url + M_RESET_URL + token;
	}

	public String getVerificationLink(HttpServletRequest request, User user) {
		String token = RandomString.make(SIZE);
		user.setToken(token);
		user.setStatus(MerchantStatus.IN_ACTIVE.toString());
		uDao.updateUser(user);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		return url + U_VERIFY_URL + token;
	}

	public String getResetPasswordLink(HttpServletRequest request, User user) {
		String token = RandomString.make(SIZE);
		user.setToken(token);
		uDao.updateUser(user);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		return url + U_VERIFY_URL + token;
	}
}
