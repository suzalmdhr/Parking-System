package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.test.dao.UserDao;
import com.test.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userByEmail = this.userDao.getUserByEmail(username);
		
		if(userByEmail == null) {
			throw new UsernameNotFoundException("User Cannot be found");
		}
		
		CustomUserDetails custom = new CustomUserDetails(userByEmail);
	
		
		return custom;
	}

}
