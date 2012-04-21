/**
 * 
 */
package com.monstersoftwarellc.virtualvote.validation;

/**
 * Interface that all classes that need Username validation.
 * @author nicholas
 *
 */
public interface IUsername {

	/**
	 * Return the UniqueKey for the persist-able object. If the object is not 
	 * saved return null.
	 * @return uniqueKey - or null if not persisted.
	 */
	public abstract Long getId();
	
	/**
	 * The username that will be validated.
	 * @return
	 */
	public abstract String getUsername();
	
}
