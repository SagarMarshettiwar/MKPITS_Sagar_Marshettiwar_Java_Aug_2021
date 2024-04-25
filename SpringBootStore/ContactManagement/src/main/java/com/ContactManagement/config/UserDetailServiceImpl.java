
  package com.ContactManagement.config;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.security.core.userdetails.UserDetails; import
  org.springframework.security.core.userdetails.UserDetailsService; import
  org.springframework.security.core.userdetails.UsernameNotFoundException;
  
  import com.ContactManagement.Repository.UserRepository; import
  com.ContactManagement.entities.User;
  
  public class UserDetailServiceImpl implements UserDetailsService{
  
	  @Autowired UserRepository userrep;
	  
	  @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		  User user = userrep.getUserbyusername(username);
	  if(user == null) { 
		  throw new UsernameNotFoundException("Could not found User!!"); 
	  }
	  
	  CustomUserDetails customUserDetails=new CustomUserDetails(user); 
	  return customUserDetails; 
	  }
  
  }
 