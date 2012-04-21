/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.exceptions;

/**
 * @author Nick(Work)
 *
 */
public class NullValueException extends PropertyValueException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5087805690280656412L;
	
	/**
	 * @param string
	 */
	public NullValueException() {
		super("nullValue.message");
	}

	/**
	 * @param string
	 */
	public NullValueException(String message) {
		super(message);
	}

}
