package com.twinkle.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.twinkle.api.domain.GooglePojo;
import com.twinkle.api.domain.PersistentLogin;
import com.twinkle.api.service.JwtService;
import com.twinkle.api.service.PersistentLoginService;
import com.twinkle.api.service.UserService;
import com.twinkle.api.service.facebook.RestFB;
import com.twinkle.api.service.google.GoogleUtils;

/**
 * @author cuonglv
 *
 */
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private final static String TOKEN_HEADER = "API_TOKEN";
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PersistentLoginService persistentLoginService;
	
	@Autowired
	private GoogleUtils googleUtils;
	
	@Autowired
	private RestFB restFb;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		if (authToken != null && authToken.trim().length() > 0) {
			UserDetails userDetail = null;
			
			if(authToken.contains("GoogleToken")) {
				authToken = authToken.replaceAll("GoogleToken", "").trim();
				
				GooglePojo googlePojo = googleUtils.getUserInfo(authToken);
				userDetail = googleUtils.buildUser(googlePojo);
			} else if (authToken.contains("FacebookToken")){
				authToken = authToken.replaceAll("FacebookToken", "").trim();
				
				com.restfb.types.User user = restFb.getUserInfo(authToken);
				userDetail = restFb.buildUser(user);
			} else {
				if (jwtService.validateTokenLogin(authToken)) {
					String username = jwtService.getUsernameFromToken(authToken);
					com.twinkle.api.domain.User user = userService.findByUsername(username);
					if (user != null) {
						boolean enabled = true;
						boolean accountNonExpired = true;
						boolean credentialsNonExpired = true;
						boolean accountNonLocked = true;
						userDetail = new User(username, user.getPassword(), enabled, accountNonExpired,
								credentialsNonExpired, accountNonLocked, user.getAuthorities());
					}
				} else {
					PersistentLogin persistentLogin = persistentLoginService.findByToken(authToken);
					
					if(persistentLogin != null) {
						persistentLogin.setStatus(false);
						
						persistentLoginService.update(persistentLogin);
					}
				}
			} 
			
			if(userDetail != null) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
						null, userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}
