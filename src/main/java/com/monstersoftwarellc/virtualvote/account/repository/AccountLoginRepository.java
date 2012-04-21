/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.monstersoftwarellc.virtualvote.account.AccountLogin;


/**
 * @author Nick(Work)
 *
 */
public interface AccountLoginRepository extends GraphRepository<AccountLogin> {
	
	/**
	 * Find {@link AccountLogin} by its {@link AccountLogin#getUsername()}.
	 * @param username
	 * @return
	 */
	AccountLogin findByUsername(String username);

}
