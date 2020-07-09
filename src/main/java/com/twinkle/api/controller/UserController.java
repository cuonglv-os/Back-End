package com.twinkle.api.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twinkle.api.domain.User;
import com.twinkle.api.dto.UserDto;
import com.twinkle.api.service.UserService;
import com.twinkle.api.utils.ObjectFactory;

/**
 * @author cuonglv
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	/* ---------------- GET ALL USER ------------------------ */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser() {
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}

	/* ---------------- GET USER BY ID ------------------------ */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable Long id) {
		User user = userService.findById(id);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
	}

	/* ---------------- CREATE NEW USER ------------------------ */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@NotNull @RequestBody UserDto userDto) {
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

				result = "Created!";
				httpStatus = HttpStatus.CREATED;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}

	/* ---------------- DELETE USER ------------------------ */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
		if(userService.delete(id)) {
			return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Delete fail!", HttpStatus.BAD_REQUEST);
	}

}
