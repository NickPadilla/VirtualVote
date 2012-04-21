/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.monstersoftwarellc.virtualvote.account.Account;
import com.monstersoftwarellc.virtualvote.account.AccountLogin;

/**
 * @author nicholas
 *
 */
public interface AccountRepository extends GraphRepository<Account> {

	/**
	 * Find {@link Account} by {@link AccountLogin#getUsername()}. 
	 * @param username
	 * @return
	 */
	Account findByLoginUsername(String username);
}
