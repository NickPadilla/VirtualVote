/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.exceptions;

/**
 * @author Nick(Work)
 *
 */
public class EmptyValueException extends PropertyValueException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7992026719348062298L;
	
	/**
	 * @param string
	 */
	public EmptyValueException() {
		super("emptyValue.message");
	}

	/**
	 * @param string
	 */
	public EmptyValueException(String message) {
		super(message);
	}

}
