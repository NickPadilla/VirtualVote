/**
 * 
 */
package com.monstersoftwarellc.virtualvote.validation;


/**
 * @author nicholas
 *
 */
public interface IUsernameValidation<T extends IUsername> {
	
	/**
	 * This method needs to check an already saved {@link IUsername} object.
	 * Ensure the value isn't changed from the persisted object, if it is different
	 * check the new value against the storage medium.  Just delegate to {@link #isUsernameUnique(IUsername)}
	 * <br/><br/>
	 * <b>Note: We only call this method if {@link IUsername#getId()} is NOT null.</b>
	 * @param userNameObject
	 * @return <code>true</code> if username is the same as saved value OR is unique in storage <br/> 
	 * 						<code>false</code> if new value is not unique.
	 */
	public abstract boolean isAlreadySavedUsernameUnique(T userNameObject);
	
	/**
	 * Check to see if this {@link IUsername#getUsername()} is unique in storage medium.
	 * 	<br/><br/>
	 * <b>Note: We only call this method if {@link IUsername#getId()} IS null.</b>
	 * @param userNameObject
	 * @return
	 */
	public abstract boolean isUsernameUnique(T userNameObject);

}
