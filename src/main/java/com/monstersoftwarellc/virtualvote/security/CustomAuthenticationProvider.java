/**
 * 
 */
package com.monstersoftwarellc.virtualvote.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.monstersoftwarellc.virtualvote.account.Account;
import com.monstersoftwarellc.virtualvote.account.Role;
import com.monstersoftwarellc.virtualvote.account.repository.AccountRepository;
import com.monstersoftwarellc.virtualvote.account.utility.PasswordUtility;

/**
 * @author nicholas
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOG = Logger.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private AccountRepository accountRepository;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,"");

		UsernamePasswordAuthenticationToken authenticationToken = null;
		UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)authentication;
		String password = (String) authentication.getCredentials();
		String username = userToken.getName();

		// support guest users
		if(password != null){			
			if(StringUtils.isEmpty(username)) {
				throw new BadCredentialsException("Authentication.emptyUsername");
			}
			if(password.length() == 0) {
				LOG.debug("Rejecting empty password for user " + username);
				throw new BadCredentialsException("Authentication.emptyPassword");
			}

			Account account = accountRepository.findByLoginUsername(username);
			if(account != null){
				if(PasswordUtility.passwordValidation(password, account.getLogin().getPassword())){
					//TODO: need to implement these settings
					boolean enabled = true;
					boolean accountNonExpired = true;
					boolean credentialsNonExpired = true;
					boolean accountNonLocked = true;
	
					Collection<? extends GrantedAuthority> authorities = getAuthorities(account.getRoles());
	
					User user = new User(account.getLogin().getUsername(), 
							account.getLogin().getPassword(),
							enabled,
							accountNonExpired,
							credentialsNonExpired,
							accountNonLocked,
							authorities);
	
					authenticationToken = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);	
				}else{
					throw new BadCredentialsException("Authentication.passwordsDoNotMatch");
				}
			}else{
				throw new UsernameNotFoundException("Authentication.userNotFound");
			}
		}

		return authenticationToken;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
		List<GrantedAuthority> authList = getGrantedAuthorities(roles);
		return authList;
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(roles != null){
			for (Role role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.toString()));
			}
		}
		return authorities;
	}

}
