/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.virtualvote.ApplicationContextTest;
import com.monstersoftwarellc.virtualvote.account.service.IAccountService;

/**
 * @author nicholas
 *
 */
public class AccountTest extends ApplicationContextTest {

	private static final Logger LOG = Logger.getLogger(AccountTest.class);

	@Autowired
	private IAccountService accountService;
	
	@Test
	@Transactional
	public void save(){
		Account account = getAccount();
		accountService.save(account);
		// ensure all objects were saved.
		testAccount(account);
		if(LOG.isDebugEnabled()){
			LOG.debug("Save() : " + account);
		}
	}

	@Test
	@Transactional
	public void nestedSave(){
		Account account = getAccount();
		accountService.save(account);
		testAccount(account);
		if(LOG.isDebugEnabled()){
			LOG.debug("nestedSave() BEFORE : " + account);
		}
		// ensure all objects were saved.
		long id = account.getId();
		account = null;
		// now lets update login and see if we can save it without
		// having to use the AccountLoginRepository
		Account accountTest = accountService.getAccountRepository().findOne(id);
		accountTest.getLogin().setUsername("newUserName");
		// have to save 
		account = accountService.save(accountTest);
		testAccount(account);
		if(LOG.isDebugEnabled()){
			LOG.debug("nestedSave() After : " + account);
		}
		assertEquals(account.getLogin().getUsername(), "newUserName");
	}
	
	private void testAccount(Account account){
		assertNotNull(account.getId());
		assertNotNull(account.getLogin().getId());
		assertNotNull(account.getPerson().getId());
		assertNotNull(account.getRoles());
	}
	
	/**
	 * @return
	 */
	private Account getAccount() {
		Account account = new Account();
		AccountLogin login = new AccountLogin();
		login.setLastLoggedIn(new Date());
		login.setPassword("12345678");
		login.setPasswordVerify("12345678");
		login.setUsername("nikoPadilla");
		account.setLogin(login);
		Person person = new Person();
		person.setFirstName("Nick");
		person.setMiddleName("P.");
		person.setLastName("Padilla");
		account.setPerson(person);
		return account;
	}
}
