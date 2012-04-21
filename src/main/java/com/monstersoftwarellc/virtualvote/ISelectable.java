/**
 * 
 */
package com.monstersoftwarellc.virtualvote;

/**
 * @author Nick(Work)
 *
 */
public interface ISelectable {
	
	/**
	 * Return the label to show the user.
	 * @return
	 */
	public abstract String getLabel();
	
	/**
	 * Return the value for the given selectable.
	 * @return
	 */
	public abstract Object getValue();

}
