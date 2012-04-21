/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

/**
 * @author Nick(Work)
 *
 */
public interface IFilter {

	/**
	 * Do we filter out the given IData object.  We will 
	 * @return true if we are to ignore this IData object.
	 */
	public abstract boolean doWeFilter(IData data);
}
