/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.exceptions;

/**
 * @author nicholas
 *
 */
public class FileSecurityException extends PropertyValueException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param message
	 */
	public FileSecurityException() {
		super("fileSecurity.message");
	}
	/**
	 * @param message
	 */
	public FileSecurityException(String message) {
		super(message);
	}

}
