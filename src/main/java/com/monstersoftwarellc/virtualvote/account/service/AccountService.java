/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account.service;

import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.virtualvote.account.Account;
import com.monstersoftwarellc.virtualvote.account.AccountLogin;
import com.monstersoftwarellc.virtualvote.account.Person;
import com.monstersoftwarellc.virtualvote.account.Role;
import com.monstersoftwarellc.virtualvote.account.repository.AccountLoginRepository;
import com.monstersoftwarellc.virtualvote.account.repository.AccountRepository;
import com.monstersoftwarellc.virtualvote.account.repository.PersonRepository;
import com.monstersoftwarellc.virtualvote.account.utility.PasswordUtility;

/**
 * @author nicholas
 *
 */
@Service
public class AccountService implements IAccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountLoginRepository accountLoginRepository;
	@Autowired
	private PersonRepository personRepository;

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.account.service.IAccountService#save(com.monstersoftwarellc.virtualvote.account.Account)
	 */
	// TODO: ensure this is 100% tested.
	@Override
	public Account save(Account account) {
		if(account.getId() != null){
			// get repository value
			AccountLogin loginFromRepo = accountLoginRepository.findOne(account.getLogin().getId());
			if(!setRoles(account)){
				AccountLogin login = account.getLogin();
				Person person = account.getPerson();
				// if the login we have here is not equal to the one from storage we save it
				if((login.getId() == null) || (login.getId() != null && !login.equals(loginFromRepo))){
					login = encryptPasswordFieldsIfNeeded(login, loginFromRepo);
					login = accountLoginRepository.save(login);
					account.setLogin(login);
				}
				
				// if the person we have here is not equal to the one from storage we save it
				if((person.getId() == null) || (person.getId() != null && !person.equals(personRepository.findOne(person.getId())))){
					person = personRepository.save(person);
					account.setPerson(person);
				}
			}else{
				account.setLogin(encryptPasswordFieldsIfNeeded(account.getLogin(), loginFromRepo));
			}
		}else{
			account.setLogin(PasswordUtility.handlePasswordEncryption(account.getLogin()));
			setRoles(account);
		}
		
		// save account - fields are now up to date
		account = accountRepository.save(account);
		return account;
	}

	/**
	 * @param login
	 */
	private AccountLogin encryptPasswordFieldsIfNeeded(AccountLogin login, AccountLogin loginFromRepo) {
		// if the login we have here is not equal to the one from storage we save it
		if((login.getId() == null) || (login.getId() != null && !login.equals(loginFromRepo))){
			// do password encryption - if needed - just test as is - since we save both fields and encrypt them
			// if they don't match we know something has changed and validation has cleared - so encrypt
			if(!loginFromRepo.getPassword().equals(login.getPassword())){
				login.setPassword(PasswordUtility.encodePassword(login.getPassword()));
				login.setPasswordVerify(PasswordUtility.encodePassword(login.getPasswordVerify()));
			}
		}
		return login;
	}

	/**
	 * @param account
	 * @param roleChange
	 * @return
	 */
	private boolean setRoles(Account account) {
		boolean roleChange = false;
		// setup roles - TODO: add more advanced Role support.
		if(account.getRoles() != null && !account.getRoles().isEmpty()){
			if(!account.getRoles().contains(Role.USER)){
				HashSet<Role> roles = new HashSet<Role>();
				roles.addAll(account.getRoles());
				roles.add(Role.USER);
				account.setRoles(roles);
				roleChange = true;
			}
		}else{
			account.setRoles(Collections.singleton(Role.USER));
			roleChange = true;
		}
		// hack: for now - if the username is nikoPadilla or nickPadilla give Admin rights
		if(account.getLogin().getUsername().equals("nikoPadilla")
				|| account.getLogin().getUsername().equals("nickPadilla")
				&& !account.getRoles().contains(Role.ADMIN)){
			HashSet<Role> roles = new HashSet<Role>();
			roles.addAll(account.getRoles());
			roles.add(Role.ADMIN);
			account.setRoles(roles);
			roleChange = true;
		}
		return roleChange;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.account.service.IAccountService#getAccountRepository()
	 */
	@Override
	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

}
