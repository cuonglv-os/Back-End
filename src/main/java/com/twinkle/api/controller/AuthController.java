package com.twinkle.api.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.twinkle.api.domain.PersistentLogin;
import com.twinkle.api.domain.Role;
import com.twinkle.api.domain.User;
import com.twinkle.api.domain.UserRole;
import com.twinkle.api.dto.UserDto;
import com.twinkle.api.service.AuthService;
import com.twinkle.api.service.JwtService;
import com.twinkle.api.service.PersistentLoginService;
import com.twinkle.api.service.UserRoleService;
import com.twinkle.api.service.UserService;
import com.twinkle.api.service.facebook.RestFB;
import com.twinkle.api.service.google.GoogleUtils;
import com.twinkle.api.utils.ObjectFactory;

/**
 * @author cuonglv
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private PersistentLoginService persistentLoginService;

	@Autowired
	private GoogleUtils googleUtils;

	@Autowired
	private RestFB restFb;
	
	@Autowired
	private Environment env;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, @NotNull @RequestBody UserDto userDto) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			User userDb = userService.findByUsername(userDto.getUserName());

			if (authService.checkLogin(userDto.getUserName(), userDto.getPassword(), userDb)) {
				if (!Strings.isNullOrEmpty(authService.getTokenFromDb(userDb))) {
					result = authService.getTokenFromDb(userDb);
				} else {
					result = jwtService.generateTokenLogin(userDto.getUserName());

					PersistentLogin persistentLogin = new PersistentLogin();
					persistentLogin.setToken(result);
					persistentLogin.setStatus(true);
					persistentLogin.setUser(userDb);
					persistentLogin.setCreateDate(new Date());

					persistentLoginService.add(persistentLogin);
				}

				httpStatus = HttpStatus.OK;
			} else {
				result = "Wrong userId and password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> register(HttpServletRequest request, @NotNull @RequestBody UserDto userDto) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			User dbUser = userService.findByUsername(userDto.getUserName());

			if (dbUser != null) {
				result = "User already exists";
				httpStatus = HttpStatus.BAD_REQUEST;
			} else {
				User user = ObjectFactory.createUser(userDto);
				userService.create(user);

				UserRole userRole = new UserRole();
				userRole.setUser(user);
				userRole.setRole(new Role(2L, "ROLE_USER"));
				userRoleService.create(userRole);

				result = "Created!";
				httpStatus = HttpStatus.CREATED;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}

	@RequestMapping(value = "/google", method = RequestMethod.GET)
	public void google(HttpServletResponse response) throws ClientProtocolException, IOException {
		response.sendRedirect("https://accounts.google.com/o/oauth2/auth?scope=email"
				+ "&redirect_uri=" + env.getProperty("google.redirect.uri") 
				+ "&response_type=code"
				+ "&client_id=" + env.getProperty("google.app.id")
				+ "&approval_prompt=force");
	}

	@RequestMapping("/login-google")
	public ResponseEntity<String> loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return new ResponseEntity<String>("Login google fail", HttpStatus.BAD_REQUEST);
		}

		String accessToken = "GoogleToken " + googleUtils.getToken(code);

		return new ResponseEntity<String>(accessToken, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public void facebook(HttpServletResponse response) throws ClientProtocolException, IOException {
		response.sendRedirect("https://www.facebook.com/dialog/oauth?" 
				+ "&client_id=" + env.getProperty("facebook.app.id")
				+ "&redirect_uri=" + env.getProperty("facebook.redirect.uri"));
	}

	@RequestMapping("/login-facebook")
	public ResponseEntity<String> loginFacebook(HttpServletRequest request)
			throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return new ResponseEntity<String>("Login facebook fail", HttpStatus.BAD_REQUEST);
		}
		
		String accessToken = "FacebookToken " + restFb.getToken(code);

		return new ResponseEntity<String>(accessToken, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		HttpStatus httpStatus = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			result = "Logout successful!";
			httpStatus = HttpStatus.OK;
		} else {
			result = "Logout fail!";
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}
}
