/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

/**
 * @author Nick(Work)
 *
 */
public interface IDataType {

	/**
	 * The DataType for this IDataType.  Will enable us to only search for the types specified here when doing
	 * index operations.
	 * @return
	 */
	public abstract DataType getDataType();
	
	/**
	 * Get the ITransformer that can make some transformations on a given IData object.
	 * @param transformer
	 */
	public abstract ITransformer getDataTransformer();
}
