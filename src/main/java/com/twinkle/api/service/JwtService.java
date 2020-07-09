package com.twinkle.api.service;

/**
 * @author cuonglv
 *
 */
public interface JwtService {
	String generateTokenLogin(String username);
	
	String getUsernameFromToken(String token);
	
	Boolean validateTokenLogin(String token);
}
