/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account.service;

import com.monstersoftwarellc.virtualvote.account.Account;
import com.monstersoftwarellc.virtualvote.account.Role;
import com.monstersoftwarellc.virtualvote.account.repository.AccountRepository;

/**
 * @author nicholas
 *
 */
public interface IAccountService {
	
	/**
	 * Go through the operations to save nested fields.  Will also provide the default {@link Role#USER} if 
	 * no {@link Role} is already applied.
	 * @param account
	 * @return
	 */
	public abstract Account save(Account account);
	
	/**
	 * Get the {@link AccountRepository}. 
	 * @return
	 */
	public abstract AccountRepository getAccountRepository();

}
