/**
 * 
 */
package com.monstersoftwarellc.virtualvote.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monstersoftwarellc.virtualvote.account.AccountLogin;
import com.monstersoftwarellc.virtualvote.account.repository.AccountLoginRepository;

/**
 * Will validate the username using the Neo4j backend.  
 * TODO: do we want to test everything as lowercase? Or even save the username as lowercase?
 * @author nicholas
 *
 */
@Component
public class UsernameValidation implements IUsernameValidation<AccountLogin> {
	
	@Autowired
	private AccountLoginRepository accountLoginRepository;

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.validation.IUsernameValidation#isAlreadySavedUsernameUnique(java.lang.Object)
	 */
	@Override
	public boolean isAlreadySavedUsernameUnique(AccountLogin object) {
		AccountLogin fresh = accountLoginRepository.findOne(object.getId());
		boolean isUnique = false;
		// check to see if both are equal - means no change - return true;
		if(fresh.getUsername().equals(object.getUsername())){
			isUnique = true;
		}else{
			// not the same - so we need to check the new value
			isUnique = isUsernameUnique(object);
		}
		return isUnique;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.validation.IUsernameValidation#isUsernameUnique(java.lang.Object)
	 */
	@Override
	public boolean isUsernameUnique(AccountLogin object) {
		boolean ret = false;
		// if we can't find the username in storage then we know the value is unique 
		if(accountLoginRepository.findByUsername(object.getUsername()) == null){
			ret = true;
		}
		return ret;
	}

}
